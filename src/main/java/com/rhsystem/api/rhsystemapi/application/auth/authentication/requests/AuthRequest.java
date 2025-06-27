package com.rhsystem.api.rhsystemapi.application.auth.authentication.requests;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank(message = "User is required")
    private String user;

    @NotBlank(message = "Password is required")
    private String password;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
