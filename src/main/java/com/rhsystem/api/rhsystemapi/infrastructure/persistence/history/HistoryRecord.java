package com.rhsystem.api.rhsystemapi.infrastructure.persistence.history;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryRepository;
import com.rhsystem.api.rhsystemapi.domain.history.event.EntityPersistenceType;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGenerator;
import com.rhsystem.api.rhsystemapi.domain.history.event.HistoryGeneratorFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Service class responsible for recording history logs for domain entities.
 * This component interacts with the {@link HistoryRepository} to persist
 * historical records and utilizes a domain-specific {@link HistoryGenerator}
 * to generate history entries based on the type of persistence operation.
 * <p>
 * The {@code HistoryRecord} captures changes to entities within the system,
 * including creation, update, and deletion, and ensures these changes are logged
 * for audit or tracking purposes.
 */
@Component
public class HistoryRecord {

    /**
     * Repository instance for performing CRUD operations on {@link com.rhsystem.api.rhsystemapi.domain.history.History} entities.
     * This variable is used to persist, retrieve, and manage history records,
     * ensuring that changes to domain entities are properly logged and stored for audit purposes.
     */
    private final HistoryRepository historyRepository;

    /**
     * Logger instance for the {@code HistoryRecord} class.
     * This logger is used to log messages and warnings related
     * to the processing of history records, including errors that
     * occur during the record generation and persistence process.
     */
    private final Logger logger = Logger.getLogger(HistoryRecord.class.getName());

    /**
     * Constructs a new {@code HistoryRecord} instance with the specified {@code HistoryRepository}.
     * This constructor initializes the repository used for persisting and retrieving history records,
     * enabling the service to log changes to domain entities for audit and tracking purposes.
     *
     * @param historyRepository the repository instance used for managing {@link com.rhsystem.api.rhsystemapi.domain.history.History} entities
     */
    public HistoryRecord(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * Records a history log for the given domain entity, capturing the specified persistence type
     * (e.g., creation, update, deletion). This method generates a history entry using a domain-specific
     * {@code HistoryGenerator} and persists it using the {@code HistoryRepository}.
     *
     * @param <T>    the type of the domain entity, which must extend {@code DomainEntity<ID>}
     * @param <ID>   the type of the unique identifier for the domain entity
     * @param domain the domain entity for which the history record will be created
     * @param type   the type of persistence action being performed on the entity (e.g., CREATE, UPDATE, DELETE)
     */
    @Transactional
    public <T extends DomainEntity<ID>, ID> void record(T domain, EntityPersistenceType type) {
        try {
            HistoryGenerator<T> gen = HistoryGeneratorFactory.getGenerator(domain);
            historyRepository.save(gen.generate(type));
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
