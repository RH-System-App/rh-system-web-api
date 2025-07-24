package com.rhsystem.api.rhsystemapi.domain.history;

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
public class HistoryEntity {

    private final String entityName;
    private final String entityId;

    public HistoryEntity(String entityName, String entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public static HistoryEntity of(String entityName, String entityId) {
        return new HistoryEntity(entityName, entityId);
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    @Override
    public String toString() {
        return "HistoryEntity{" + "entityName=" + entityName + ", entityId=" + entityId + '}';
    }

}
