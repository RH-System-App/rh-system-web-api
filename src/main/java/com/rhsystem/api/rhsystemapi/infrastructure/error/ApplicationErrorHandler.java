package com.rhsystem.api.rhsystemapi.infrastructure.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.exceptions.GenericError;
import com.rhsystem.api.rhsystemapi.core.error.ApplicationError;
import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Global error handler for the application. This class handles exceptions thrown by controllers and
 * provides a structured response for validation errors and general exceptions.
 */
@RestControllerAdvice
public class ApplicationErrorHandler {

    /**
     * Handles MethodArgumentNotValidException, which occurs when validation fails for a method
     * argument. It constructs a structured error response containing the timestamp, status, error type,
     * and details of the validation errors.
     *
     * @param ex the MethodArgumentNotValidException that was thrown
     *
     * @return a ResponseEntity containing the structured error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationError> handleValidationBeanValidationExceptions(
            MethodArgumentNotValidException ex) {
        var error = this.createError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação");
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            error.addDetail(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles general exceptions that are not specifically caught by other handlers. It constructs a
     * structured error response containing the timestamp, status, error type, and message.
     *
     * @param ex the Exception that was thrown
     *
     * @return a ResponseEntity containing the structured error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApplicationError> handleGeneralException(Exception ex) {
        var error = this.createError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro Interno do Servidor");
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    /**
     * Handles ValidationException, which is a custom exception used for validation errors. It
     * constructs a structured error response containing the timestamp, status, error type, and details
     * of the validation errors.
     *
     * @param ex the ValidationException that was thrown
     *
     * @return a ResponseEntity containing the structured error response
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationError> handleValidationException(ValidationException ex) {
        var error = this.createError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação");
        ex.getErrors().forEach(err -> error.addDetail(err.getField(), err.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        var error = this.createError(HttpStatus.BAD_REQUEST.value(), "Erro de Formato de Requisição");
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ifx) {
            String fieldName = ifx.getPath().stream().map(JsonMappingException.Reference::getFieldName)
                                  .collect(Collectors.joining("."));
            String detailMessage =
                    String.format("O valor '%s' não é um formato válido para o campo '%s'.", ifx.getValue(), fieldName);
            error.addDetail(fieldName, detailMessage);
        } else if (cause instanceof JsonMappingException jme) {
            jme.getPath().stream().findFirst().ifPresent(ref -> {
                String fieldName = ref.getFieldName();
                String detailMessage = "O campo '" + fieldName + "' está malformado ou ilegível.";
                error.addDetail(fieldName, detailMessage);
            });
        } else if (cause != null) {
            error.addDetail("requestBody", "O corpo da requisição está malformado ou ilegível.");

        } else {
            error.addDetail("requestBody", "O corpo da requisição está malformado ou ilegível.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(GenericError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApplicationError> handleGenericError(GenericError ex) {
        var error = createError(HttpStatus.BAD_REQUEST.value(), ex.getErrorType());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private ApplicationError createError(int status, String errorType) {
        return new ApplicationError(status, errorType);
    }
}
