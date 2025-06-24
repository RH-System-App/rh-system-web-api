package com.rhsystem.api.rhsystemapi.infrastructure.persistence.auth.recoverpassword;

import com.rhsystem.api.rhsystemapi.infrastructure.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataJpaRecoverPasswordRepository extends JpaRepository<RecoverPasswordEntity, UUID> {

    Optional<RecoverPasswordEntity> findByRecoverCode(UUID recoverCode);
    Optional<RecoverPasswordEntity> findByUser(UserEntity user);
}
