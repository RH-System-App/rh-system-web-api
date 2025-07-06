package com.rhsystem.api.rhsystemapi.infrastructure.mapper;

import com.rhsystem.api.rhsystemapi.core.ObjectMapper;
import com.rhsystem.api.rhsystemapi.core.valueobject.EntityKey;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.Functionality;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality.FunctionalityEntity;
import org.springframework.stereotype.Component;

@Component
public class FunctionalityMapper extends ObjectMapper<Functionality, FunctionalityEntity> {

    @Override
    public Functionality toDomain(FunctionalityEntity entity) {
        var functionality = new Functionality();
        functionality.setName(entity.getDescription());
        functionality.setKey(EntityKey.of(entity.getCode()));
        return functionality;
    }

    @Override
    public FunctionalityEntity toEntity(Functionality domain) {
        var entity = new FunctionalityEntity();
        entity.setCode(domain.getKey().getValue());
        entity.setDescription(domain.getName());
        return entity;
    }
}
