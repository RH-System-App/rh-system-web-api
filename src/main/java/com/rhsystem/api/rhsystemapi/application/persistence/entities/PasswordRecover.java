package com.rhsystem.api.rhsystemapi.application.persistence.entities;

import com.rhsystem.api.rhsystemapi.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "RH_PASSWORD_RECOVER")
public class PasswordRecover {

    @Id
    @Column(name = "USER_ID")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Column(name = "RECOVER_CODE", unique = true, nullable = false, updatable = false)
    private UUID recoverCode = UUID.randomUUID();

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDateTime expirationDate;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UUID getRecoverCode() {
        return this.recoverCode;
    }

    public void setRecoverCode(UUID recoverCode) {
        this.recoverCode = recoverCode;
    }

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return this.expirationDate.isBefore(LocalDateTime.now());
    }
}
