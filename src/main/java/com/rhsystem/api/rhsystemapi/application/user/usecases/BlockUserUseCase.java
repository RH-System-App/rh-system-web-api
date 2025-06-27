package com.rhsystem.api.rhsystemapi.application.user.usecases;

import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.domain.user.UserStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for blocking a user account. This class handles the business logic
 * for locking a user's account status based on their username.
 */
@Component
public class BlockUserUseCase {

    /**
     * A repository for user-related data operations. This instance is responsible for accessing
     * and manipulating user information in the underlying data store, including operations
     * such as finding users by username and updating their account status.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a BlockUserUseCase with the specified user repository.
     *
     * @param userRepository the repository for accessing and managing user data
     */
    public BlockUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles the process of locking a user's account based on the provided username.
     * This method retrieves the user associated with the provided
     */
    @Transactional
    public void handle(String userName) {
        var userWithUserName = userRepository.findByUserName(userName)
                                             .orElseThrow(() -> new ValidationException("userName", "User not found"));
        userRepository.updateStatus(userWithUserName, UserStatus.LOCKED);
    }
}
