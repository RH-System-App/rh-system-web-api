package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataFunctionalityRepository extends JpaRepository<FunctionalityEntity, String> {

    Optional<FunctionalityEntity> findByCode(String code);
}
