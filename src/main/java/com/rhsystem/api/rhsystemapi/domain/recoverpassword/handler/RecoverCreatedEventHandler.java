package com.rhsystem.api.rhsystemapi.domain.recoverpassword.handler;

import com.rhsystem.api.rhsystemapi.core.event.EventHandler;
import com.rhsystem.api.rhsystemapi.core.mail.ApplicationMailSender;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPassword;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.event.RecoverCreatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RecoverCreatedEventHandler
        implements EventHandler<RecoverCreatedEvent> {


    private static final Logger logger = LogManager.getLogger();
    private final ApplicationMailSender mailSender;

    public RecoverCreatedEventHandler(ApplicationMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @EventListener
    @Async
    public void handle(RecoverCreatedEvent event) {
        logger.info("Recover created for user {} : ", event.getDomainEntity().getUser().getEmail());
        sendMessageToUser(event.getDomainEntity());
    }


    private void sendMessageToUser(RecoverPassword recover) {
        try {
            Map<String, Object> values = Map.of("code", recover.getRecoverCode());
            this.mailSender.sendMail(recover.getUser().getEmail(), "recover_password_mail", values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
