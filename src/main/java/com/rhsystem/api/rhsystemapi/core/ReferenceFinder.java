package com.rhsystem.api.rhsystemapi.core;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

public interface ReferenceFinder<T, D extends DomainEntity<?>> {

    T findReference(D domain);
}
