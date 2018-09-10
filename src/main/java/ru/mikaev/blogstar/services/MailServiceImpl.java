package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.mikaev.blogstar.entities.ActivationType;

@Service
@PropertySources({
        @PropertySource("classpath:ru/mikaev/blogstar/mail.properties")
})
public class MailServiceImpl implements MailService {
    @Autowired
    private MailSender mailSender;

    @Value("${email.registration.message}")
    private String emailRegistrationMessage;

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
        String messageText = String.format(emailRegistrationMessage, name, ActivationType.EMAIL.toString(), activationCode);
        send(emailTo, "Activate your account in Blogstar", messageText);

        return activationCode;
    }}
