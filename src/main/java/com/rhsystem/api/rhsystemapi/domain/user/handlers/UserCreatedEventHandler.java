package com.rhsystem.api.rhsystemapi.domain.user.handlers;

import com.rhsystem.api.rhsystemapi.core.event.EventHandler;
import com.rhsystem.api.rhsystemapi.domain.user.event.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventHandler implements EventHandler<UserCreatedEvent> {

    @Override
    @EventListener
    @Async
    public void handle(UserCreatedEvent event) {
        System.out.println("User created: " + event.getUser());
    }

}
