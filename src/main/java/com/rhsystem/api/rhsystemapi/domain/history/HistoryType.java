package com.rhsystem.api.rhsystemapi.domain.history;

/**
 * Represents the types of changes or events that can be recorded in a system's history.
 * This enumeration is used to specify the nature of an operation performed on a domain entity,
 * such as an update, deletion, or creation.
 * <p>
 * The available types are:
 * - UPDATE: Indicates that an existing entity or its properties have been updated.
 * - DELETE: Indicates that an entity has been removed from the system.
 * - CREATE: Indicates that a new entity has been created in the system.
 * <p>
 * This enumeration is primarily used in conjunction with history tracking components, such as
 * {@link HistoryInfo}, to provide a clear and standardized way of categorizing changes.
 */
public enum HistoryType {
    UPDATE,
    DELETE,
    CREATE
}
