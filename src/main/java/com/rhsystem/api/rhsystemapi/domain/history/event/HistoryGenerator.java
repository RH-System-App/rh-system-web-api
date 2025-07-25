package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.SpringContext;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.History;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryInfo;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryType;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.infrastructure.security.UserDetailIml;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class HistoryGenerator<T extends DomainEntity<?>> {


    protected T domain;

    public HistoryGenerator(T domain) {
        this.domain = domain;
    }


    public History generate(EntityPersistenceType type) {
        return generate(type, null);
    }

    public abstract History generate(EntityPersistenceType type, T old);


    protected abstract String getEntityName();


    protected History createHistory(EntityPersistenceType type) {
        History h = new History();
        h.setUser(getCurrentUser());
        return h;
    }

    protected HistoryInfo createInfo(String property, String oldValue, String newValue, HistoryType type) {
        HistoryInfo info = new HistoryInfo();
        info.setProperty(property);
        info.setType(type);
        info.setValue(newValue);
        info.setValue(oldValue);
        return info;
    }


    public User getCurrentUser() {
        UserDetailIml detail = (UserDetailIml) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = detail.getUsername();
        return SpringContext.getBean(UserRepository.class).findByUserName(userName).orElse(null);
    }
}
