package com.rhsystem.api.rhsystemapi.core.persistence;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

import java.util.Optional;

/**
 * Generic interface for managing domain entities and performing CRUD operations.
 *
 * @param <T> the type of domain entity managed by this repository, which extends {@link DomainEntity}
 * @param <K> the type of the unique identifier used by the domain entity
 */
public interface Repository<T extends DomainEntity<K>, K> {

    /**
     * Persists the specified domain entity to the underlying data store. If the entity already exists,
     * it updates the existing record. If it does not exist, it creates a new record.
     *
     * @param domain the domain entity to be saved or updated
     * @return the saved or updated domain entity
     */
    T save(T domain);

    /**
     * Finds and retrieves a domain entity using its unique identifier.
     *
     * @param id the unique identifier of the domain entity to be retrieved
     * @return the domain entity associated with the given identifier, or null if not found
     */
    Optional<T> findById(K id);

    /**
     * Deletes the specified domain entity from the underlying data store.
     *
     * @param domain the domain entity to be deleted
     */
    void delete(T domain);

}
