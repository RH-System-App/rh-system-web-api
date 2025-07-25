package com.rhsystem.api.rhsystemapi.domain.history;

/**
 * Repository interface for managing {@link History} entities.
 * Provides methods to persist and retrieve history records based on specific criteria.
 */
public interface HistoryRepository {

    /**
     * Persists a given history record in the underlying data store.
     * This method is used to save or update the state of a {@link History} entity, allowing
     * for the creation or modification of historical records within the system.
     *
     * @param history the {@link History} object containing the details of the historical event or record to be saved
     * @return the persisted {@link History} entity, potentially updated with additional information such as an ID or timestamp
     */
    History save(History history);

    /**
     * Retrieves a {@link History} record based on the provided entity and entity key.
     * This method is used to find and return a historical record associated with a specific
     * entity and its unique identifier within the system.
     *
     * @param entity    the name of the entity for which the history record is being retrieved
     * @param entityKey the unique identifier associated with the specified entity
     * @return the {@link History} record matching the given entity and entity key, or null if no record is found
     */
    History findByEntityAndEntityKey(String entity, String entityKey);

}
