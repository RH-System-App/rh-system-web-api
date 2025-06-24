package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChangePasswordRequest {

    @NotNull(message = "Recovery Code is required")
    private UUID recoverCode;

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public UUID getRecoverCode() {
        return this.recoverCode;
    }

    public void setRecoverCode(UUID recoverCode) {
        this.recoverCode = recoverCode;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
