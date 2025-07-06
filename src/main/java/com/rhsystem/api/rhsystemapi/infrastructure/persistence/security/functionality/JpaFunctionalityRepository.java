package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality;

import com.rhsystem.api.rhsystemapi.domain.security.functionality.Functionality;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.FunctionalityRepository;
import com.rhsystem.api.rhsystemapi.infrastructure.mapper.FunctionalityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaFunctionalityRepository implements FunctionalityRepository {

    private final SpringDataFunctionalityRepository springDataFunctionalityRepository;
    private final FunctionalityMapper mapper;

    public JpaFunctionalityRepository(SpringDataFunctionalityRepository springDataFunctionalityRepository, FunctionalityMapper mapper) {
        this.springDataFunctionalityRepository = springDataFunctionalityRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Functionality> findByCode(String code) {
        return springDataFunctionalityRepository.findByCode(code).map(mapper::toDomain);
    }
}
