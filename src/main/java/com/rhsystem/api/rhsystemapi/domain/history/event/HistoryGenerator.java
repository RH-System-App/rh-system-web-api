package com.rhsystem.api.rhsystemapi.domain.history.event;

import com.rhsystem.api.rhsystemapi.core.SpringContext;
import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.history.History;
import com.rhsystem.api.rhsystemapi.domain.history.HistoryBuilder;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.infrastructure.security.UserDetailIml;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Abstract class for generating history records for a specific domain entity.
 * This class provides a framework for capturing changes to an entity, including
 * the type of persistence action (e.g., create, update, delete), the entity's new
 * and old states, and the user responsible for the action.
 *
 * @param <T> The type of the domain entity this generator handles, which must extend {@code DomainEntity<?>}.
 */
public abstract class HistoryGenerator<T extends DomainEntity<?>> {

    /**
     * The domain entity for which this {@code HistoryGenerator} is responsible.
     * This field represents the current state of the domain entity and is used
     * for generating history records that capture entity-specific changes over time.
     */
    protected T domain;

    /**
     * Constructs a new instance of {@code HistoryGenerator} for the specified domain entity.
     *
     * @param domain the domain entity for which history records will be generated
     */
    public HistoryGenerator(T domain) {
        this.domain = domain;
    }


    /**
     * Generates a history record for the associated domain entity based on the specified persistence type.
     * This method serves as a shortcut to invoke {@link #generate(EntityPersistenceType, T)} with a null old state.
     *
     * @param type the type of persistence action being performed (e.g., CREATE, UPDATE, DELETE)
     * @return the generated history record containing detailed information about the action
     * @throws Exception if an error occurs while generating the history record
     */
    public History generate(EntityPersistenceType type) throws Exception {
        return generate(type, null);
    }

    /**
     * Generates a history record for the associated domain entity based on the specified
     * persistence type and the previous state of the entity.
     *
     * @param type the type of persistence action being performed (e.g., CREATE, UPDATE, DELETE)
     * @param old  the previous state of the domain entity before the action; this can be null if
     *             there is no previous state
     * @return the generated {@code History} record containing detailed information
     * about the action performed, including the entity information and the user responsible
     * @throws Exception if an error occurs while generating the history record
     */
    public History generate(EntityPersistenceType type, T old) throws Exception {
        User user = getCurrentUser();
        History h = HistoryBuilder.build(type, domain, old, user);
        h.setEntityId(domain.getKey().toString());
        h.setEntityName(getEntityName());
        return h;
    }


    /**
     * Retrieves the name of the entity associated with this {@code HistoryGenerator}.
     * This method should provide an implementation that returns the specific name
     * of the entity type handled by the generator.
     *
     * @return the name of the entity as a {@code String}
     */
    protected abstract String getEntityName();


    /**
     * Retrieves the currently authenticated user from the security context and fetches
     * the corresponding user details from the repository.
     *
     * @return the currently authenticated {@code User} if found, or {@code null} if no user is associated
     */
    public User getCurrentUser() {
        UserDetailIml detail = (UserDetailIml) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = detail.getUsername();
        return SpringContext.getBean(UserRepository.class).findByUserName(userName).orElse(null);
    }
}
