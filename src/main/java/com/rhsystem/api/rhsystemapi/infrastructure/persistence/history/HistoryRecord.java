package com.rhsystem.api.rhsystemapi.infrastructure.persistence.history;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryRepository;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGeneratorFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HistoryRecord {

    private final HistoryRepository historyRepository;

    public HistoryRecord(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public <T extends DomainEntity<ID>, ID> void record(T domain, EntityPersistenceType type) {
        HistoryGenerator<T> gen = HistoryGeneratorFactory.getGenerator(domain);
        historyRepository.save(gen.generate(type));
    }
}
