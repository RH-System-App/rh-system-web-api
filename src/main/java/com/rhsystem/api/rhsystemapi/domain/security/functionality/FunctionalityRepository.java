package com.rhsystem.api.rhsystemapi.domain.security.functionality;

import java.util.Optional;

public interface FunctionalityRepository {

    Optional<Functionality> findByCode(String code);

}
