package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases;

import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.RecoverPasswordRequest;
import com.rhsystem.api.rhsystemapi.application.persistence.repositories.PasswordRecoverRepository;
import com.rhsystem.api.rhsystemapi.core.mail.ApplicationMailSender;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RecoverPasswordUseCase {


    private final UserRepository userRepository;
    private final PasswordRecoverRepository passwordRecoverRepository;

    private final ApplicationMailSender mailSender;

    public RecoverPasswordUseCase(UserRepository userRepository, PasswordRecoverRepository passwordRecoverRepository,
                                  ApplicationMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordRecoverRepository = passwordRecoverRepository;
        this.mailSender = mailSender;
    }


    public void handle(RecoverPasswordRequest request) {
        var userByEmail = this.userRepository.findByEmail(request.getEmail());
        userByEmail.ifPresent(user -> {
//            var existingRecover = this.passwordRecoverRepository.findByUser(user);
//            existingRecover.ifPresent(this.passwordRecoverRepository::delete);
//            PasswordRecover recover = this.createRecoverByUser(user);
//            this.sendMessageToUser(user, recover);
        });
    }

//    private void sendMessageToUser(User user, PasswordRecover recover) {
//        try {
//            Map<String, Object> values = Map.of("code", recover.getRecoverCode());
//            this.mailSender.sendMail(user.getEmail(), "recover_password_mail", values);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    private PasswordRecover createRecoverByUser(User user) {
//        var passwordRecover = new PasswordRecover();
//        passwordRecover.setUser(user);
//        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
//        passwordRecover.setExpirationDate(expirationDate);
//        return this.passwordRecoverRepository.save(passwordRecover);
//    }
}
