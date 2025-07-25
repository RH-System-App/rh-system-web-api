package com.rhsystem.api.rhsystemapi.domain.history;

import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * Represents a lightweight entity containing basic identification details for
 * the associated historical record. The HistoryEntity class defines the entity's
 * name and a unique identifier, which act as references for identifying the
 * specific context or object related to a history record.
 * <p>
 * This class is primarily used within the domain model to provide a reference
 * to an entity associated with a specific history record without including
 * additional details or relationships.
 */
public class HistoryKey {

    /**
     * Represents the name of the entity associated with the historical record.
     * This field serves as an identifier for the type or class of the entity being tracked,
     * providing context to the historical data and helping to distinguish between different
     * types of entities within the system.
     */
    private final String entityName;

    /**
     * Represents the unique identifier of the entity associated with the historical record.
     * This field is used to distinguish a specific entity instance within its type or category,
     * thereby providing a reference for tracking changes or events related to the entity.
     * The value of this field is immutable and uniquely identifies the entity in the context of
     * the history system.
     */
    private final String entityId;

    /**
     * Represents a unique key associated with a historical record in the system.
     * This field serves as an identifier for tracking and referencing specific
     * history entries. The value of this field is immutable and uniquely
     * identifies the record within its context.
     */
    private final Long key;


    /**
     * Constructs a new instance of the HistoryKey class with the specified attributes.
     *
     * @param key        the unique key associated with the historical record
     * @param entityName the name of the entity associated with the historical record
     * @param entityId   the unique identifier of the entity associated with the historical record
     */
    public HistoryKey(Long key, String entityName, String entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.key = key;
    }

    /**
     * Creates a new instance of the HistoryKey class with a randomly generated unique key,
     * using the provided entity name and entity ID.
     *
     * @param entityName the name of the entity associated with the historical record
     * @param entityId   the unique identifier of the entity associated with the historical record
     * @return a new HistoryKey instance initialized with a generated key, entity name, and entity ID
     */
    public static HistoryKey of(String entityName, String entityId) {
        return new HistoryKey(Random.from(RandomGenerator.getDefault()).nextLong(1, 500), entityName, entityId);
    }

    /**
     * Retrieves the name of the entity associated with the current instance.
     *
     * @return the name of the entity
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Retrieves the unique identifier of the entity associated with the current instance.
     *
     * @return the unique identifier of the entity
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * Retrieves the unique key associated with the historical record.
     *
     * @return the unique key as a Long
     */
    public Long getKey() {
        return key;
    }


    @Override
    public String toString() {
        return
                entityName +
                        "-"
                        + key + "-" +
                        entityId;
    }
}
