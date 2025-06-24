package com.rhsystem.api.rhsystemapi.application.user.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a request to create a new user. This class contains the necessary fields for user
 * creation, along with validation annotations to ensure the data is valid.
 */
public class CreateUserRequest {

    /**
     * The name of the user. This field is required and cannot be blank.
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * The email of the user. This field is required, cannot be blank, and must be a valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The password for the user. This field is required and cannot be blank.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
