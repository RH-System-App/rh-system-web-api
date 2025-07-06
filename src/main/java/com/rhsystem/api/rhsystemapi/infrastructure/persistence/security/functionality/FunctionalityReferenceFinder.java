package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality;

import com.rhsystem.api.rhsystemapi.core.ReferenceFinder;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.Functionality;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class FunctionalityReferenceFinder implements ReferenceFinder<FunctionalityEntity, Functionality> {

    private final EntityManager entityManager;


    public FunctionalityReferenceFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public FunctionalityEntity findReference(Functionality domain) {
        return entityManager.getReference(FunctionalityEntity.class, domain.getKey().getValue());
    }
}
