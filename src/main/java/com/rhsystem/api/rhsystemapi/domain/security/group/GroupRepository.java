package com.rhsystem.api.rhsystemapi.domain.security.group;

/**
 * Repository interface for managing {@code Group} entities. It provides abstraction
 * for saving and persisting {@code Group} instances to the underlying storage mechanism.
 */
public interface GroupRepository {

    /**
     * Saves the specified {@code Group} entity to the underlying data store. This method
     * ensures that the provided {@code Group} instance is persisted and can be retrieved
     * or updated in the future.
     *
     * @param group the {@code Group} entity to be saved; must not be null
     * @return the saved {@code Group} entity, potentially with updated state after persistence
     */
    Group save(Group group);
}
