package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RecoverPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
