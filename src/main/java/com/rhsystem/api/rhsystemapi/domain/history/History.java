package com.rhsystem.api.rhsystemapi.domain.history;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a history event or record within the system. This class tracks key attributes
 * such as the name of the event, the user who performed the action, the moment it occurred,
 * and relevant additional information encapsulated in the HistoryInfo instances.
 * <p>
 * The History class serves as a domain entity that organizes and structures historical events
 * for proper auditability and traceability within the system.
 */
public class History extends DomainEntity<HistoryEntity> {

    private User user;
    private LocalDateTime moment;
    private Collection<HistoryInfo> info = new ArrayList<>();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public Collection<HistoryInfo> getInfo() {
        return info;
    }

    public void setInfo(Collection<HistoryInfo> info) {
        this.info = info;
    }
}
