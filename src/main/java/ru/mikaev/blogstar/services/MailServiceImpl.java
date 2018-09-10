package ru.mikaev.blogstar.services;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.mikaev.blogstar.entities.ActivationType;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySources({
        @PropertySource("classpath:ru/mikaev/blogstar/mail.properties")
})
public class MailServiceImpl implements MailService {
    @Autowired
    private MailSender mailSender;

    @Value("${email.registration.subject}")
    private String emailRegistrationSubject;

    @Value("${email.registration.message}")
    private String emailRegistrationMessage;

    @Value("${email.newemail.subject}")
    private String newEmailSubject;

    @Value("${email.newemail.message}")
    private String newEmailMessage;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private ActivationService activationService;

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public String sendRegisterMessage(String emailTo, String name) {
        String activationCode = activationService.generateActivationCode();

        Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("activationType", ActivationType.REGISTRATION.toString());
        values.put("activationCode", activationCode);
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        String messageText = sub.replace(emailRegistrationMessage);

        send(emailTo, emailRegistrationSubject, messageText);

        return activationCode;
    }

    @Override
    public String sendChangeEmailMessage(String emailTo) {
        String activationCode = activationService.generateActivationCode();

        Map<String, String> values = new HashMap<>();
        values.put("activationType", ActivationType.CHANGE_EMAIL.toString());
        values.put("activationCode", activationCode);
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        String messageText = sub.replace(newEmailMessage);

        send(emailTo, newEmailSubject, messageText);

        return activationCode;
    }
}
