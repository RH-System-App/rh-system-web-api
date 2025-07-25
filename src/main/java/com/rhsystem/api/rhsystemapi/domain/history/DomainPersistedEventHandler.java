package com.rhsystem.api.rhsystemapi.domain.history;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.event.DomainPersistedEvent;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.history.HistoryRecord;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DomainPersistedEventHandler {

    private final HistoryRecord historyGenerator;


    public DomainPersistedEventHandler(HistoryRecord historyGenerator) {
        this.historyGenerator = historyGenerator;
    }

    @EventListener
    public <T extends DomainEntity<ID>, ID> void onDomainPersisted(DomainPersistedEvent<T, ID> evt) {
        historyGenerator.record(evt.getDomainEntity(), evt.getType());
    }
}
