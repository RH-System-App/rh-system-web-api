package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases;

import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.RecoverPasswordRequest;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPassword;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPasswordRepository;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.event.RecoverCreatedEvent;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.infrastructure.event.ApplicationEventDispatcher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RecoverPasswordUseCase {

    private final UserRepository userRepository;
    private final RecoverPasswordRepository recoverPasswordRepository;

    private final ApplicationEventDispatcher eventPublisher;

//    private final ApplicationMailSender mailSender;


    public RecoverPasswordUseCase(UserRepository userRepository,
                                  RecoverPasswordRepository recoverPasswordRepository,
                                  ApplicationEventDispatcher eventPublisher) {
        this.userRepository = userRepository;
        this.recoverPasswordRepository = recoverPasswordRepository;
        this.eventPublisher = eventPublisher;
    }


    @Transactional
    public void handle(RecoverPasswordRequest request) {
        var userByEmail = this.userRepository.findByEmail(request.getEmail());
        userByEmail.ifPresent(user -> {
            var existingRecover = this.recoverPasswordRepository.findByUser(user);
            existingRecover.ifPresent(this.recoverPasswordRepository::delete);
            RecoverPassword recover = this.createRecoverByUser(user);
            eventPublisher.dispatch(createRecoverEvent(recover));
        });
    }

    private RecoverCreatedEvent createRecoverEvent(RecoverPassword recover) {
        return new RecoverCreatedEvent(recover);
    }


    private RecoverPassword createRecoverByUser(User user) {
        var passwordRecover = new RecoverPassword();
        passwordRecover.setUser(user);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
        passwordRecover.setExpirationDate(expirationDate);
        passwordRecover.setRecoverCode(UUID.randomUUID());
        return this.recoverPasswordRepository.save(passwordRecover);
    }
}
