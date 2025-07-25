package com.rhsystem.api.rhsystemapi.core;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

/**
 * A generic interface for finding a reference to an entity based on a corresponding domain object.
 * It serves as a bridge to retrieve or map domain objects to their persistent entity references.
 *
 * @param <T> the type of the entity to retrieve
 * @param <D> the type of the domain object used as input for the retrieval
 */
public interface ReferenceFinder<T, D extends DomainEntity<?>> {

    /**
     * Finds and retrieves an entity reference corresponding to the given domain object.
     * The method serves as a bridge to map a domain object to its associated persistent entity reference.
     *
     * @param domain the domain object for which the entity reference is to be retrieved
     * @return the entity reference corresponding to the given domain object
     */
    T findReference(D domain);
}
