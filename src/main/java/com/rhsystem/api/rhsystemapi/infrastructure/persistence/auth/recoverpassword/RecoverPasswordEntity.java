package com.rhsystem.api.rhsystemapi.infrastructure.persistence.auth.recoverpassword;

import com.rhsystem.api.rhsystemapi.infrastructure.persistence.converters.UUIDToStringConverter;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "RH_PASSWORD_RECOVER")
public class RecoverPasswordEntity {

    @Id
    @Column(name = "USER_ID", nullable = false, length = 36)
    @Convert(converter = UUIDToStringConverter.class)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Column(name = "RECOVER_CODE", length = 36, unique = true, nullable = false, updatable = false)
    @Convert(converter = UUIDToStringConverter.class)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID recoverCode;

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

}
