package com.rhsystem.api.rhsystemapi.core.valueobject;

import java.util.Objects;

/**
 * Represents a value object in the system. A value object is an object that contains attributes but
 * has no conceptual identity. It is defined only by its attributes and is immutable.
 *
 * @param <T> the type of the value contained in this value object
 */
public abstract class ValueObject<T> {

    /**
     * The value contained in this value object. It is immutable and defines the identity of the value
     * object.
     */
    private final T value;

    /**
     * Constructs a ValueObject with the specified value.
     *
     * @param value the value to be encapsulated in this value object
     */
    public ValueObject(T value) {
        this.value = value;
    }

    /**
     * Returns the value contained in this value object.
     *
     * @return the value of type T
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Checks if this value object is equal to another object. Two value objects are considered equal if
     * their values are equal.
     *
     * @param o the object to compare with
     *
     * @return true if the values are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ValueObject<?> that = (ValueObject<?>) o;
        return Objects.equals(this.value, that.value);
    }

    /**
     * Returns the hash code of this value object. The hash code is based on the value contained in this
     * value object.
     *
     * @return the hash code of the value
     */
    @Override
    public int hashCode() {
        return this.value != null ? this.value.hashCode() : super.hashCode();
    }


}
