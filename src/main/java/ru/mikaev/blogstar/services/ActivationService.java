package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.User;

public interface ActivationService {
    String generateActivationCode();
    boolean doActivate(String code, ActivationType activationType);
    void bind(User user, String activationCode, ActivationType activationType);
}
