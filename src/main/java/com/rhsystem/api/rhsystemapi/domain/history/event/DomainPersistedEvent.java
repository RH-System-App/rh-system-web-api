package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

public class DomainPersistedEvent<T extends DomainEntity<ID>, ID> extends DomainEvent<T> {

    private final EntityPersistenceType type;

    public DomainPersistedEvent(T domainEntity, EntityPersistenceType type) {
        super(domainEntity);
        this.type = type;
    }

    public EntityPersistenceType getType() {
        return type;
    }
}
