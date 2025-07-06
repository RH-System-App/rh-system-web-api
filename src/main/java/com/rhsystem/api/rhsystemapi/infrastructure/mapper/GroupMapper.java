package com.rhsystem.api.rhsystemapi.infrastructure.mapper;

import com.rhsystem.api.rhsystemapi.core.ObjectMapper;
import com.rhsystem.api.rhsystemapi.core.valueobject.EntityKey;
import com.rhsystem.api.rhsystemapi.domain.security.group.Group;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.group.GroupEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper extends ObjectMapper<Group, GroupEntity> {

    private final FunctionalityMapper functionalityMapper;

    public GroupMapper(FunctionalityMapper functionalityMapper) {
        this.functionalityMapper = functionalityMapper;
    }

    @Override
    public Group toDomain(GroupEntity entity) {
        var group = new Group();
        group.setName(entity.getName());
        group.setKey(EntityKey.of(entity.getId()));
        group.setFunctionalities(entity.getFunctionalities().stream().map(functionalityMapper::toDomain).toList());
        return group;
    }

    @Override
    public GroupEntity toEntity(Group domain) {
        var entity = new GroupEntity();
        if (domain.getKey() != null) {
            entity.setId(domain.getKey().getValue());
        }
        entity.setName(domain.getName());
        entity.setFunctionalities(domain.getFunctionalities().stream().map(functionalityMapper::toEntity).toList());
        return entity;
    }
}
