package com.rhsystem.api.rhsystemapi.application.user.http.presenters;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a data presenter for user information. This class is used to encapsulate
 * user-related data such as name and email in a format suitable for external presentation.
 * <p>
 * The {@code UserPresenter} class is part of the presentation layer and is primarily
 * intended for use in responses to API requests, ensuring that only the necessary
 * user information is exposed outside the system.
 * <p>
 * For example, this class might be used in the UserController when retrieving or listing
 * users via REST APIs.
 */
public class UserPresenter {

    @Schema(description = "Nome completo do usuário.", example = "John Doe",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "E-mail único do usuário.", example = "john.doe@example.com")
    private String email;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
