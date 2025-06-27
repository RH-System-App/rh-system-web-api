package com.rhsystem.api.rhsystemapi.domain.user;

import java.util.Optional;

/**
 * Defines a repository interface for managing {@link User} objects in the application.
 * This interface provides abstraction for operations related to persisting and retrieving User entities.
 */
public interface UserRepository {

    User save(User u);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);

    void updateStatus(User user, UserStatus status);

    Optional<User> findByUserName(String userName);
}
