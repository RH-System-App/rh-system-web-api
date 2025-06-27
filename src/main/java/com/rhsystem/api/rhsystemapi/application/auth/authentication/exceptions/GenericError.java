package com.rhsystem.api.rhsystemapi.application.auth.authentication.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public class GenericError extends RuntimeException {

    @JsonIgnore
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private String errorType = "GenericError";

    GenericError(String message) {
        super(message);
    }

    GenericError(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    GenericError() {
        super("Unexpected error in server");
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonValue
    public Integer getStatusCode() {
        return this.status.value();
    }
}
