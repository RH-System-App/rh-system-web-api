package com.rhsystem.api.rhsystemapi.domain.recoverpassword;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecoverPassword extends DomainEntity<UUID> {

    private User user;
    private UUID recoverCode;
    private LocalDateTime expirationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getRecoverCode() {
        return recoverCode;
    }

    public void setRecoverCode(UUID recoverCode) {
        this.recoverCode = recoverCode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return this.expirationDate.isBefore(LocalDateTime.now());
    }
}
