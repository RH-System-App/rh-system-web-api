package com.rhsystem.api.rhsystemapi.application.user.usecases;

import com.rhsystem.api.rhsystemapi.application.user.presenters.UserCreatedPresenter;
import com.rhsystem.api.rhsystemapi.application.user.requests.CreateUserRequest;
import com.rhsystem.api.rhsystemapi.core.event.EventDispatcher;
import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.domain.user.UserStatus;
import com.rhsystem.api.rhsystemapi.domain.user.event.UserCreatedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;

/**
 * Use case for creating a new user. This class handles the business logic for user creation,
 * including validation and username generation.
 */
@Component
public class CreateUserUseCase {

    /**
     * Repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * Password encoder for encoding user passwords.
     */
    private final PasswordEncoder passwordEncoder;

    private final EventDispatcher eventPublisher;

    /**
     * Constructs a CreateUserUseCase with the specified user repository and password encoder.
     *
     * @param userRepository  the repository for accessing user data
     * @param passwordEncoder the password encoder for encoding user passwords
     */
    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, EventDispatcher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles the creation of a new user based on the provided request. This method performs validation
     * on the request, generates a username, encodes the password
     *
     * @param request the request containing user details for creation
     *
     * @return UserCreatedPresenter containing the username of the created user
     *
     * @throws ValidationException if validation fails, containing details of the validation errors
     * @see ValidationException
     */
    @Transactional
    public UserCreatedPresenter handle(CreateUserRequest request) {

        this.validate(request);

        User u = this.createUser(request);

        eventPublisher.dispatch(new UserCreatedEvent(u));

        var result = new UserCreatedPresenter();
        result.setUsername(u.getUserName());
        return result;
    }

    private void validate(CreateUserRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("email", "Already exists a user with this same email");
        }
    }

    /**
     * Creates a new user based on the provided request. This method sets the user's name, email,
     * encodes the password, and generates a username before saving the user to the repository.
     *
     * @param request the request containing user details for creation
     *
     * @return the created User object
     */
    private User createUser(CreateUserRequest request) {
        User u = new User();
        u.setName(request.getName());
        u.setEmail(request.getEmail());
        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
        u.setUserName(this.generateUniqueUsername(request.getName()));
        u.setStatus(UserStatus.PENDING);
        return this.userRepository.save(u);
    }


    /**
     * Generates a unique username by checking the database. If "john.smith" exists, it will try
     * "john.smith1", "john.smith2", etc.
     *
     * @param fullName The full name to base the username on.
     *
     * @return A username string that is guaranteed to be unique at the time of the call.
     */
    private String generateUniqueUsername(String fullName) {
        String baseUsername = this.generateBaseUsername(fullName);
        String finalUsername = baseUsername;
        int sequence = 1; // Start trying with suffix 2

        // Loop while the generated username already exists in the database
        while (this.userRepository.existsByUserName(finalUsername)) {
            finalUsername = baseUsername + sequence;
            sequence++;
        }

        return finalUsername;
    }

    /**
     * Generates the base username from the full name (e.g., "John da Silva" -> "john.silva").
     */
    private String generateBaseUsername(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "user";
        }

        String normalizedName = Normalizer.normalize(fullName, Normalizer.Form.NFD).replaceAll("\\p{M}", "");

        String[] nameParts = normalizedName.trim().toLowerCase().split("\\s+");

        if (nameParts.length == 1) {
            return nameParts[0];
        }

        return nameParts[0] + "." + nameParts[nameParts.length - 1];
    }

}
