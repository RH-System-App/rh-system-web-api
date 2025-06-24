package com.rhsystem.api.rhsystemapi.core.error;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ApplicationError {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    private final Map<String, String> details = new LinkedHashMap<>();


    public ApplicationError(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getDetails() {
        return this.details;
    }

    public void addDetail(String field, String message) {
        this.details.put(field, message);
    }

    public void addDetails(Map<String, String> details) {
        this.details.putAll(details);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
