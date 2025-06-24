package com.rhsystem.api.rhsystemapi.core.event;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

public interface EventDispatcher {

    <T extends DomainEntity> void dispatch(DomainEvent<T> event);

}
