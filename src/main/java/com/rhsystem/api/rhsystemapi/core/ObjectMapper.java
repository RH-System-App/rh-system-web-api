package com.rhsystem.api.rhsystemapi.core;

import java.util.stream.StreamSupport;

/**
 * A generic mapper interface for converting between domain objects and entities.
 *
 * @param <D> the type of the domain object
 * @param <E> the type of the entity
 */
public abstract class ObjectMapper<D, E> {

    /**
     * Converts an entity object to its corresponding domain object.
     *
     * @param entity the entity object to be converted
     *
     * @return the converted domain object
     */
    public abstract D toDomain(E entity);

    /**
     * Converts a domain object to its corresponding entity object.
     *
     * @param domain the domain object to be converted
     *
     * @return the converted entity object
     */
    public abstract E toEntity(D domain);

    /**
     * Converts a collection of entity objects to their corresponding domain objects.
     *
     * @param entities an iterable collection of entity objects to be converted
     *
     * @return an iterable collection of converted domain objects
     */
    public Iterable<D> toDomain(Iterable<E> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::toDomain).toList();
    }

    /**
     * Converts a collection of domain objects to their corresponding entity objects.
     *
     * @param domains an iterable collection of domain objects to be converted
     *
     * @return an iterable collection of converted entity objects
     */
    public Iterable<E> toEntity(Iterable<D> domains) {
        return StreamSupport.stream(domains.spliterator(), false).map(this::toEntity).toList();
    }

}
