package com.rhsystem.api.rhsystemapi.domain.user;

import com.rhsystem.api.rhsystemapi.domain.history.History;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryInfo;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryType;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;

import java.util.List;

public class UserHistoryGenerator extends HistoryGenerator<User> {

    public static final String ENTITY_NAME = "user";

    public UserHistoryGenerator(User domain) {
        super(domain);
    }

    @Override
    public History generate(EntityPersistenceType type, User old) {
        History history = createHistory(type);
        if (type == EntityPersistenceType.CREATE) {
            HistoryInfo name = createInfo("Nome", domain.getName(), "-", HistoryType.CREATE);
            HistoryInfo mail = createInfo("E-mail", domain.getEmail(), "-", HistoryType.CREATE);
            history.getInfo().addAll(List.of(name, mail));
            return history;
        }
        return history;
    }


    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }
}
