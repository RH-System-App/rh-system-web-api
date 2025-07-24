package com.rhsystem.api.rhsystemapi.domain.user;

import com.rhsystem.api.rhsystemapi.domain.history.History;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;

public class UserHistoryGenerator extends HistoryGenerator<User> {

    public UserHistoryGenerator(User domain) {
        super(domain);
    }

    @Override
    public History generate(EntityPersistenceType type, User old) {
        return null;
    }
}
