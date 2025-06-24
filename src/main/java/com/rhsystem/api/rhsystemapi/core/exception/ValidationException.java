package com.rhsystem.api.rhsystemapi.core.exception;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a validation exception that contains a collection of validation errors. This exception
 * is thrown when validation fails in the application.
 */
public class ValidationException extends RuntimeException {

    /**
     * A collection of validation errors that occurred during validation.
     */
    private Collection<ValidationError> errors = new ArrayList<>();

    /**
     * Constructs a ValidationException with no errors.
     */
    public ValidationException() {
    }

    /**
     * Constructs a ValidationException with a single validation error.
     *
     * @param errors the validation errors to be added to the exception
     */
    public ValidationException(Collection<ValidationError> errors) {
        this.errors = errors;
    }

    /**
     * Constructs a ValidationException with a specific field and message.
     *
     * @param field   the field that caused the validation error
     * @param message the message describing the validation error
     */
    public ValidationException(String field, String message) {
        this.errors.add(new ValidationError(field, message));
    }

    /**
     * Constructs a ValidationException with a specific message.
     *
     * @param message the message describing the validation error
     */
    public Collection<ValidationError> getErrors() {
        return this.errors;
    }

    /**
     * Sets the collection of validation errors for this exception.
     *
     * @param errors the collection of validation errors to be set
     */
    public void setErrors(Collection<ValidationError> errors) {
        this.errors = errors;
    }

    /**
     * Adds a validation error to the collection of errors in this exception.
     *
     * @param error the validation error to be added
     */
    public void addError(ValidationError error) {
        this.errors.add(error);
    }

    /**
     * Adds a validation error with a specific field and message to the collection of errors in this
     * exception.
     *
     * @param field   the field that caused the validation error
     * @param message the message describing the validation error
     */
    public void addError(String field, String message) {
        this.errors.add(new ValidationError(field, message));
    }

    /**
     * Checks if there are any validation errors in this exception.
     *
     * @return true if there are validation errors, false otherwise
     */
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

}
