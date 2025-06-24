package com.rhsystem.api.rhsystemapi.infrastructure.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.core.event.EventDispatcher;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventDispatcher implements EventDispatcher {

    private final ApplicationEventPublisher publisher;

    public ApplicationEventDispatcher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    
    @Override
    public <T extends DomainEntity> void dispatch(DomainEvent<T> event) {
        publisher.publishEvent(event);
    }
}
