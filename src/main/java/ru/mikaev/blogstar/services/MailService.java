package ru.mikaev.blogstar.services;

public interface MailService {
    void send(String emailTo, String subject, String message);
    String sendRegisterMessage(String emailTo, String name);
}
