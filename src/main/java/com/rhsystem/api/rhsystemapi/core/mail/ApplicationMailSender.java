package com.rhsystem.api.rhsystemapi.core.mail;

import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import com.rhsystem.api.rhsystemapi.core.template.TemplateProcessor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class ApplicationMailSender {

    private final TemplateProcessor templateProcessor;
    private final JavaMailSender javaMailSender;


    public ApplicationMailSender(TemplateProcessor templateProcessor, JavaMailSender javaMailSender) {
        this.templateProcessor = templateProcessor;
        this.javaMailSender = javaMailSender;
    }


    public void sendMail(String to, String template, Map<String, Object> model) throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("nao-responda@rhsystem.com");
        helper.setTo(to);
        helper.setSubject("Password Reset Request");

        String body = this.templateProcessor.process(template, model);

        helper.setText(body, true);

        this.javaMailSender.send(message);
    }

}
