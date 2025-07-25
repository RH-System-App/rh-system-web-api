package com.rhsystem.api.rhsystemapi.domain.history.event;

/**
 * Represents the type of persistence action performed on a domain entity.
 * This enumeration is used to identify whether an entity is being created, updated, or deleted
 * as part of a persistence operation.
 * <p>
 * The available values are:
 * - CREATE: Indicates the creation of a new entity.
 * - UPDATE: Indicates the update of an existing entity.
 * - DELETE: Indicates the removal of an existing entity.
 */
public enum EntityPersistenceType {
    CREATE, UPDATE, DELETE
}
