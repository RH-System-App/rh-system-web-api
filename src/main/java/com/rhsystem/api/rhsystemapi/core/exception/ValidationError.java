package com.rhsystem.api.rhsystemapi.core.exception;

/**
 * Represents a validation error with a field and a message.
 */
public class ValidationError {

    /**
     * The field that caused the validation error.
     */
    private String field;

    /**
     * The message describing the validation error.
     */
    private String message;

    /**
     * Constructs a ValidationError with the specified field and message.
     * 
     * @param field the field that caused the validation error
     * @param message the message describing the validation error
     */
    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    /**
     * Returns the field that caused the validation error.
     * 
     * @return the field that caused the validation error
     */
    public String getField() {
        return this.field;
    }

    /**
     * Sets the field that caused the validation error.
     * 
     * @param field the field that caused the validation error
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Returns the message describing the validation error.
     * 
     * @return the message describing the validation error
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message describing the validation error.
     * 
     * @param message the message describing the validation error
     */
    public void setMessage(String message) {
        this.message = message;
    }


}
