package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases;

import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.ChangePasswordRequest;
import com.rhsystem.api.rhsystemapi.application.persistence.repositories.PasswordRecoverRepository;
import com.rhsystem.api.rhsystemapi.core.exception.ValidationException;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.domain.user.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordUseCase {

    private final PasswordRecoverRepository passwordRecoverRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public ChangePasswordUseCase(PasswordRecoverRepository passwordRecoverRepository, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder, UserMapper mapper) {
        this.passwordRecoverRepository = passwordRecoverRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }


    public void handle(ChangePasswordRequest request) {
        var recoverOpt = this.passwordRecoverRepository.findByRecoverCode(request.getRecoverCode());
        if (recoverOpt.isEmpty()) {
            throw new ValidationException("code", "Invalid recovery code");
        } else {
            var recover = recoverOpt.get();

            if (recover.isExpired()) {
                this.passwordRecoverRepository.delete(recover);
                throw new ValidationException("code", "Recovery code has expired");
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                throw new ValidationException("confirmPassword", "Passwords do not match");
            }

            var user = recover.getUser();
            user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
            this.userRepository.save(mapper.toDomain(user));
            this.passwordRecoverRepository.delete(recover);
        }
    }

}
