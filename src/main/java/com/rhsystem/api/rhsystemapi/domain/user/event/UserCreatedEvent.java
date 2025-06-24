package com.rhsystem.api.rhsystemapi.domain.user.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.domain.user.User;

public class UserCreatedEvent extends DomainEvent {

    private final User user;

    public UserCreatedEvent(User user) {
        super(user.getKey());
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

}
