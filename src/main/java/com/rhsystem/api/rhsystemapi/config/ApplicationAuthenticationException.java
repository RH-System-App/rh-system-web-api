package com.rhsystem.api.rhsystemapi.config;

import com.fasterxml.jackson.annotation.JsonValue;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.exceptions.GenericError;
import org.springframework.http.HttpStatus;

public class ApplicationAuthenticationException extends GenericError {

    private final String path;

    public ApplicationAuthenticationException(String message, String errorType, HttpStatus status, String path) {
        super(status, message);
        setErrorType(errorType);
        this.path = path;
    }

    @JsonValue
    public String getPath() {
        return path;
    }
}
