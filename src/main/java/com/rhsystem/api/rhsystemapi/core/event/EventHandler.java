package com.rhsystem.api.rhsystemapi.core.event;

/**
 * Represents a generic interface for handling domain events in the system.
 *
 * @param <T> the type of domain event that this handler will process. It extends {@link DomainEvent},
 *            ensuring that only valid domain events are handled.
 */
public interface EventHandler<T extends DomainEvent<?>> {

    /**
     * Handles the specified domain event. This method is responsible for processing
     * or reacting to the given domain event in a manner that conforms to the implementation
     * of the handler.
     *
     * @param event the domain event to be handled, which is of type {@code T} and extends {@link DomainEvent}
     */
    void handle(T event);

}
