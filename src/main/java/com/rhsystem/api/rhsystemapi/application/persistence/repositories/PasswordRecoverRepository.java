package com.rhsystem.api.rhsystemapi.application.persistence.repositories;

import com.rhsystem.api.rhsystemapi.application.persistence.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, UUID> {

//    Optional<PasswordRecover> findByUser(User user);

    Optional<PasswordRecover> findByRecoverCode(UUID recoverCode);

}
