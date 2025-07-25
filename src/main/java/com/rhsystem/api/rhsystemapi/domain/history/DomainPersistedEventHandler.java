package com.rhsystem.api.rhsystemapi.domain.history;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.event.DomainPersistedEvent;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.history.HistoryRecord;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Handles events related to the persistence of domain entities and facilitates the recording
 * of historical data upon such events. This class listens to domain persistence events and
 * triggers the generation of corresponding history records.
 * <p>
 * The functionality provided by this class ensures that any changes or persistence actions
 * performed on domain entities within the system are properly tracked and stored for
 * auditing and traceability purposes. It integrates with the HistoryRecord component
 * to persist the generated historical data.
 * <p>
 * Responsibilities:
 * - Listens for domain entity persistence events.
 * - Captures the entity and event details from the event object.
 * - Delegates the task of recording the history to the HistoryRecord component.
 * <p>
 * Thread Safety: This class relies on Spring's component model and is expected to be
 * singleton-scoped. Thread safety is maintained by avoiding mutable shared state and
 * delegating operations to thread-safe collaborators.
 * <p>
 * Annotations:
 * - {@code @Component}: Marks this class as a Spring-managed component.
 * - {@code @EventListener}: Indicates that this class handles specific domain events.
 * <p>
 * Parameters:
 * - T: Represents a domain entity type that extends {@code DomainEntity<ID>}.
 * - ID: Represents the type of the unique identifier used by the domain entity.
 */
@Component
public class DomainPersistedEventHandler {

    /**
     * Represents a component responsible for handling the creation and persistence
     * of historical records within the system. This instance is used to generate
     * and store history entries based on domain entity events and persistence actions.
     * <p>
     * The {@code historyGenerator} collaborates with the {@link HistoryRecord}
     * class to convert domain entity events into persistent history records.
     * It facilitates traceability and auditing by delegating the task of historical
     * record creation to a specialized service.
     * <p>
     * This field is immutable and initialized through dependency injection, ensuring
     * that the associated {@link HistoryRecord} instance is properly managed within
     * the class's lifecycle.
     */
    private final HistoryRecord historyGenerator;


    /**
     * Constructs a DomainPersistedEventHandler instance with the specified history generator.
     * This constructor initializes the event handler with a history generator, which is
     * responsible for recording historical data when domain persistence events are handled.
     *
     * @param historyGenerator the HistoryRecord instance used for generating historical records
     */
    public DomainPersistedEventHandler(HistoryRecord historyGenerator) {
        this.historyGenerator = historyGenerator;
    }

    /**
     * Handles domain entity persistence events by recording historical data.
     * This method is triggered when a domain entity is persisted or updated,
     * enabling the tracking of changes or actions for auditing and traceability.
     *
     * @param <T>  the type of the domain entity, extending DomainEntity<ID>
     * @param <ID> the type of the unique identifier used by the domain entity
     * @param evt  the event object containing details about the persisted domain entity
     *             and the persistence action type
     */
    @EventListener
    public <T extends DomainEntity<ID>, ID> void onDomainPersisted(DomainPersistedEvent<T, ID> evt) {
        historyGenerator.record(evt.getDomainEntity(), evt.getType());
    }
}
