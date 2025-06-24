package com.rhsystem.api.rhsystemapi.core.valueobject;

import java.util.UUID;

/**
 * Represents a unique identifier for an entity in the system. This class extends ValueObject to
 * encapsulate the UUID value.
 */
public class EntityKey extends ValueObject<UUID> {

    /**
     * Constructs an EntityKey with the specified UUID value.
     *
     * @param value the UUID value representing the entity key
     */
    public EntityKey(UUID value) {
        super(value);
    }

    /**
     * Factory method to create an EntityKey from a UUID.
     *
     * @param value the UUID value to create the EntityKey from
     * @return a new EntityKey instance
     */
    public static EntityKey of(UUID value) {
        return new EntityKey(value);
    }

}
