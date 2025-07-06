package com.rhsystem.api.rhsystemapi.core.valueobject;

/**
 * Represents a unique identifier for an entity in the system. This class extends ValueObject to
 * encapsulate the UUID value.
 */
public class EntityKey<K> extends ValueObject<K> {

    /**
     * Constructs an EntityKey with the specified UUID value.
     *
     * @param value the UUID value representing the entity key
     */
    public EntityKey(K value) {
        super(value);
    }

    /**
     * Factory method to create an EntityKey from a UUID.
     *
     * @param value the UUID value to create the EntityKey from
     * @return a new EntityKey instance
     */
    public static <K> EntityKey<K> of(K value) {
        return new EntityKey<>(value);
    }

}
