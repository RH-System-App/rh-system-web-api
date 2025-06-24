package com.rhsystem.api.rhsystemapi.infrastructure.persistence.user;

import com.rhsystem.api.rhsystemapi.core.ReferenceFinder;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class UserReferenceFinder implements ReferenceFinder<UserEntity, User> {

    private final EntityManager entityManager;

    public UserReferenceFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity findReference(User domain) {
        return entityManager.find(UserEntity.class, domain.getKey().getValue());
    }
}
