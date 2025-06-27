package com.rhsystem.api.rhsystemapi.application.auth.authentication.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Represents an exception thrown when the provided credentials do not match the expected values.
 * This is a custom runtime exception typically used to indicate errors in authentication processes.
 */
public class CredentialsNotMatch extends GenericError {

    /**
     * Constructs a new CredentialsNotMatch exception with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CredentialsNotMatch(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        setErrorType("CredentialsNotMatch");
    }

    /**
     * Constructs a new CredentialsNotMatch exception with a default detail message.
     * The default message indicates that the username or password provided is incorrect.
     */
    public CredentialsNotMatch() {
        this("Username or password is incorrect");
    }

}
