package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.event.DomainEvent;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

/**
 * Represents a persisted domain event in the system.
 * This event is triggered whenever a specific type of persistence
 * action (e.g., create, update, delete) is performed on a domain entity.
 * The class extends {@code DomainEvent<T>}, inheriting the common behavior
 * and properties associated with events in the domain.
 *
 * @param <T>  the type of the domain entity associated with this event,
 *             which must extend {@code DomainEntity<ID>}
 * @param <ID> the type of the identifier for the domain entity
 */
public class DomainPersistedEvent<T extends DomainEntity<ID>, ID> extends DomainEvent<T> {

    /**
     * Represents the type of persistence action associated with this domain persisted event.
     * This field indicates whether the domain entity has been created, updated, or deleted
     * in the context of a persistence operation.
     * <p>
     * The value is of type {@code EntityPersistenceType}, which is an enumeration defining
     * the possible persistence actions: CREATE, UPDATE, DELETE.
     * <p>
     * This field is immutable and set during the initialization of the {@code DomainPersistedEvent}.
     */
    private final EntityPersistenceType type;

    /**
     * Constructs a new {@code DomainPersistedEvent} for the specified domain entity and persistence type.
     * This event represents an action performed on the domain entity, such as creation, update, or deletion.
     *
     * @param domainEntity the domain entity associated with this event
     * @param type         the type of persistence action performed on the domain entity
     *                     (e.g., {@code CREATE}, {@code UPDATE}, {@code DELETE})
     */
    public DomainPersistedEvent(T domainEntity, EntityPersistenceType type) {
        super(domainEntity);
        this.type = type;
    }

    /**
     * Retrieves the type of the persistence action associated with this event.
     *
     * @return the {@code EntityPersistenceType} indicating the type of persistence action,
     * such as CREATE, UPDATE, or DELETE
     */
    public EntityPersistenceType getType() {
        return type;
    }
}
