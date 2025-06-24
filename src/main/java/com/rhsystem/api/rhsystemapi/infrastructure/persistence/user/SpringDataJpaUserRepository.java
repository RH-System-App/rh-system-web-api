package com.rhsystem.api.rhsystemapi.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);
}
