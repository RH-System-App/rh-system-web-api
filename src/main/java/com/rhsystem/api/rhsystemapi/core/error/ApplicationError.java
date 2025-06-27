package com.rhsystem.api.rhsystemapi.core.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a structured error response that can be used to provide detailed information
 * about errors that occur in the application. This class encapsulates information such as
 * the timestamp of the error, HTTP status code, error type, detailed validation errors, and
 * an optional message.
 * <p>
 * The class is designed to be flexible and includes functionalities for adding validation
 * details, error messages, and other contextual information about the error.
 */
public class ApplicationError {

    /**
     * Represents the timestamp at which the error occurred. This value is initialized
     * to the current date and time when the object is created.
     * <p>
     * The timestamp is immutable and indicates when the application error instance
     * was created, providing temporal context for the error occurrence.
     */
    private final LocalDateTime timestamp = LocalDateTime.now();

    /**
     * A map that holds additional details about the error. This is primarily used to provide
     * contextual information such as specific validation errors or other metadata related to
     * the error.
     * <p>
     * Key-value pairs in this map typically represent field names and their corresponding
     * error messages, aiding in identifying the root cause of the problem more precisely.
     * <p>
     * The map is initialized as a {@link LinkedHashMap} to maintain the insertion order of
     * the details, which can be useful for debugging or displaying errors.
     * <p>
     * The map is annotated with {@link JsonInclude} to ensure only non-empty details are
     * included in the JSON response.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> details = new LinkedHashMap<>();

    /**
     * Represents the HTTP status code associated with the error. This integer value corresponds
     * to the standard HTTP status codes (e.g., 404 for Not Found, 500 for Internal Server Error).
     * <p>
     * The status code helps categorize the type of error that occurred and provides a way for
     * clients to interpret and handle the error appropriately.
     */
    private HttpStatus status;

    /**
     * Represents the error message associated with a specific application error.
     * This variable holds a brief, human-readable description of the error condition.
     */
    private String error;

    /**
     * Represents a descriptive message associated with an application error.
     * This field is optional and will only be included in JSON responses if it is non-empty.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;


    /**
     * Constructs an ApplicationError instance with the specified HTTP status code and error message.
     *
     * @param status the HTTP status code representing the error
     * @param error  the error message describing the error
     */
    public ApplicationError(int status, String error) {
        this.status = HttpStatus.valueOf(status);
        this.error = error;
    }

    /**
     * Retrieves the timestamp indicating when the error occurred.
     *
     * @return the timestamp of the error occurrence as a LocalDateTime object
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Retrieves the HTTP status associated with this error.
     *
     * @return the HTTP status as an instance of {@code HttpStatus}
     */
    public HttpStatus getStatus() {
        return this.status;
    }

    /**
     * Updates the HTTP status associated with this error.
     *
     * @param status the HTTP status to be set, represented as an instance of {@code HttpStatus}
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Retrieves the error message associated with this instance.
     *
     * @return the error message as a String
     */
    public String getError() {
        return this.error;
    }

    /**
     * Updates the error message associated with this instance.
     *
     * @param error the error message to be set, represented as a String
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Retrieves the details associated with this error instance.
     *
     * @return a map containing the details of the error, where the keys represent specific fields
     * and the values represent their corresponding error messages
     */
    public Map<String, String> getDetails() {
        return this.details;
    }

    /**
     * Adds a detail entry to the error details map. Each entry consists of a field name and
     * its corresponding error message.
     *
     * @param field   the name of the field related to the error
     * @param message the error message associated with the specified field
     */
    public void addDetail(String field, String message) {
        this.details.put(field, message);
    }

    /**
     * Adds multiple entries to the error details map. Each entry in the provided map
     * consists of a field name and its corresponding error message, which will be
     * added to the existing error details.
     *
     * @param details a map containing field names as keys and their corresponding
     *                error messages as values
     */
    public void addDetails(Map<String, String> details) {
        this.details.putAll(details);
    }

    /**
     * Retrieves the message associated with this instance.
     *
     * @return the message as a String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Updates the message associated with this instance.
     *
     * @param message the message to be set, represented as a String
     */
    public void setMessage(String message) {
        this.message = message;
    }


}
