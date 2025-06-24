package com.rhsystem.api.rhsystemapi.core.event;

import com.rhsystem.api.rhsystemapi.core.valueobject.EntityKey;

import java.time.LocalDateTime;

/**
 * Represents a domain event in the system.
 */
public class DomainEvent {

    /**
     * Represents the unique identifier of the aggregate associated with this domain event.
     * The aggregate ID is encapsulated as an {@link EntityKey}, which ensures the identifier
     * is immutable and tied to a specific entity through a UUID value. This identifier enables
     * tracking of the specific aggregate instance that the event pertains to.
     */
    private final EntityKey aggregateId;

    /**
     * Represents the timestamp at which the domain event occurred. This value is immutable
     * and is initialised to the current date and time when the event instance is created.
     * It provides a precise recording of when the domain event took place.
     */
    private final LocalDateTime occurredOn = LocalDateTime.now();

    /**
     * Constructs a DomainEvent with the specified aggregate ID.
     *
     * @param aggregateId the unique identifier of the aggregate associated
     *                    with this domain event, encapsulated in an {@link EntityKey}
     */
    protected DomainEvent(EntityKey aggregateId) {
        this.aggregateId = aggregateId;
    }

    /**
     * Retrieves the unique identifier of the aggregate associated with this domain event.
     *
     * @return the unique identifier of the aggregate encapsulated as an {@link EntityKey}
     */
    public EntityKey getAggregateId() {
        return this.aggregateId;
    }

    /**
     * Retrieves the timestamp indicating when this domain event occurred.
     *
     * @return the timestamp of the event occurrence as a {@link LocalDateTime}
     */
    public LocalDateTime getOccurredOn() {
        return this.occurredOn;
    }

}
