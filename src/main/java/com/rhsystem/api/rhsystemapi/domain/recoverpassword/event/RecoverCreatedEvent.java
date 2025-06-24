package com.rhsystem.api.rhsystemapi.domain.recoverpassword.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPassword;

public class RecoverCreatedEvent extends DomainEvent<RecoverPassword> {

    public RecoverCreatedEvent(RecoverPassword domainEntity) {
        super(domainEntity);
    }
    
}
