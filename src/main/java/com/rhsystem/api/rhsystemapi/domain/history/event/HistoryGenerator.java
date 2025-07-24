package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.History;

public abstract class HistoryGenerator<T extends DomainEntity<?>> {


    protected T domain;

    public HistoryGenerator(T domain) {
        this.domain = domain;
    }


    public History generate(EntityPersistenceType type) {
        return generate(type, null);
    }

    public abstract History generate(EntityPersistenceType type, T old);


}
