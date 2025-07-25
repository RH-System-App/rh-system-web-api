package com.rhsystem.api.rhsystemapi.domain.user;

import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;

public class UserHistoryGenerator extends HistoryGenerator<User> {

    public static final String ENTITY_NAME = "user";

    public UserHistoryGenerator(User domain) {
        super(domain);
    }


    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }
}
