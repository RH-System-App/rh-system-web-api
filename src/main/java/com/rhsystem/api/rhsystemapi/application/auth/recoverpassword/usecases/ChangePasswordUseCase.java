package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases;

import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.ChangePasswordRequest;
import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPasswordRepository;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Use case for changing the user's password within the application. This class handles
 * the process of validating recovery codes, checking password requirements, and persisting
 * the updated password upon successful validation.
 * <p>
 * Responsibilities:
 * - Validates the recovery code provided by the user.
 * - Ensures that new password and confirmation password match.
 * - Checks if the recovery code is expired and deletes it if so.
 * - Updates the password of the user in the repository using encoded format.
 * - Deletes the recovery code from persistence once the password is successfully updated.
 * <p>
 * Dependencies:
 * - {@code RecoverPasswordRepository}: Handles operations related to recovery code management.
 * - {@code UserRepository}: Manages user-related persistence actions.
 * - {@code PasswordEncoder}: Encodes the new password before saving it.
 * <p>
 * Exceptions:
 * - {@code ValidationException}: Thrown in cases where validation fails, such as:
 * - Invalid recovery code.
 * - Expired recovery code.
 * - Mismatched password and confirmation password.
 */
@Component
public class ChangePasswordUseCase {

    private final RecoverPasswordRepository recoverPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordUseCase(RecoverPasswordRepository recoverPasswordRepository, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.recoverPasswordRepository = recoverPasswordRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void handle(ChangePasswordRequest request) {
        var recoverOpt = this.recoverPasswordRepository.findByRecoverCode(request.getRecoverCode());
        if (recoverOpt.isEmpty()) {
            throw new ValidationException("code", "Invalid recovery code");
        } else {
            var recover = recoverOpt.get();

            if (recover.isExpired()) {
                this.recoverPasswordRepository.delete(recover);
                throw new ValidationException("code", "Recovery code has expired");
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                throw new ValidationException("confirmPassword", "Passwords do not match");
            }

            var user = recover.getUser();
            user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
            this.userRepository.save(user);
            this.recoverPasswordRepository.delete(recover);
        }
    }

}
