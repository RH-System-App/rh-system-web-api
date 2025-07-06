package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.group;

import com.rhsystem.api.rhsystemapi.domain.security.group.Group;
import com.rhsystem.api.rhsystemapi.domain.security.group.GroupRepository;
import com.rhsystem.api.rhsystemapi.infrastructure.mapper.GroupMapper;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality.FunctionalityReferenceFinder;
import org.springframework.stereotype.Repository;

@Repository
public class JpaGroupRepository implements GroupRepository {

    private final SpringDataJpaGroupRepository springDataJpaGroupRepository;
    private final GroupMapper mapper;
    private final FunctionalityReferenceFinder referenceFinder;

    public JpaGroupRepository(SpringDataJpaGroupRepository springDataJpaGroupRepository, GroupMapper mapper, FunctionalityReferenceFinder referenceFinder) {
        this.springDataJpaGroupRepository = springDataJpaGroupRepository;
        this.mapper = mapper;
        this.referenceFinder = referenceFinder;
    }

    @Override
    public Group save(Group group) {
        GroupEntity entity = mapper.toEntity(group);
        entity.setFunctionalities(group.getFunctionalities().stream().map(referenceFinder::findReference).toList());
        entity = springDataJpaGroupRepository.save(entity);
        return mapper.toDomain(entity);
    }
}
