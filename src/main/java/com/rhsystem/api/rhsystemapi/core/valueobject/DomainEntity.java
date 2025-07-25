package com.rhsystem.api.rhsystemapi.core.valueobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents a base class for domain entities within the system. This abstract class provides a standard
 * structure for handling entity keys, equality, and hash code computation in derived classes.
 *
 * @param <K> the type of the key used to uniquely identify an entity
 */
public abstract class DomainEntity<K> {

    /**
     * Represents the unique key associated with the domain entity. This key is used as an identifier
     * for the entity within the system and encapsulates a value of type {@code K}.
     */
    private EntityKey<K> key;

    /**
     * Retrieves the unique key associated with this domain entity.
     *
     * @return the entity key of type {@code EntityKey<K>} used to uniquely identify the entity
     */
    public EntityKey<K> getKey() {
        return key;
    }

    /**
     * Sets the unique key associated with the domain entity. The key is used to uniquely
     * identify the entity within the system and is encapsulated within an {@code EntityKey<K>} object.
     *
     * @param key the entity key of type {@code EntityKey<K>} to be assigned to the domain entity
     */
    public void setKey(EntityKey<K> key) {
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainEntity<K> that = (DomainEntity<K>) o;

        return new EqualsBuilder().append(key, that.key).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(key).toHashCode();
    }
}
