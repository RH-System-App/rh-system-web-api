package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataJpaGroupRepository extends JpaRepository<GroupEntity, UUID> {

}
