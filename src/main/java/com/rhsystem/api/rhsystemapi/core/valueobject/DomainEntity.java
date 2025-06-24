package com.rhsystem.api.rhsystemapi.core.valueobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class DomainEntity {

    private EntityKey key;

    public EntityKey getKey() {
        return key;
    }

    public void setKey(EntityKey key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainEntity that = (DomainEntity) o;

        return new EqualsBuilder().append(key, that.key).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(key).toHashCode();
    }
}
