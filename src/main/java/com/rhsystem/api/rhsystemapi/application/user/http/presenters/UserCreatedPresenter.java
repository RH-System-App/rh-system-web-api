package com.rhsystem.api.rhsystemapi.application.user.http.presenters;

/**
 * Represents a presenter used to encapsulate necessary data related to the creation of a user.
 * <p>
 * This class is used in the application's presentation layer to structure and transfer
 * information about the created user. It is intended to be returned as part of the application logic
 * in operations that create a new user, ensuring that the external systems or clients only receive
 * the relevant details, such as the username of the newly created user.
 * <p>
 * Typical usage includes returning this presenter in response to successful user creation flows,
 * such as when handling a {@code CreateUserRequest}.
 * <p>
 * Responsibilities:
 * - Encapsulates the username of the newly created user.
 * <p>
 * Methods:
 * - {@code getUsername()}: Retrieves the username of the created user.
 * - {@code setUsername(String username)}: Sets the username for the created user.
 */
public class UserCreatedPresenter {

    /**
     * Stores the username of the created user.
     * <p>
     * This variable represents the unique identifier or distinguishing name
     * associated with the user that was successfully created. It is primarily
     * used to transfer and expose user-specific data in the presentation layer.
     */
    private String username;

    /**
     * Retrieves the username of the created user.
     *
     * @return the username of the created user as a string
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of the created user.
     *
     * @param username the username to be associated with the created user
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
