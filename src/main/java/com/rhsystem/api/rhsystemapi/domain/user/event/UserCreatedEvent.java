package com.rhsystem.api.rhsystemapi.domain.user.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.domain.user.User;

public class UserCreatedEvent extends DomainEvent<User> {

    public UserCreatedEvent(User user) {
        super(user);
    }


}
