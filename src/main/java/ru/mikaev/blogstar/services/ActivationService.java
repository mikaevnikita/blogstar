package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.entities.ActivationEntity;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.ActivationPairNotFoundException;

import java.rmi.activation.ActivationException;

public interface ActivationService {
    String generateActivationCode();
    void doActivate(String code, ActivationType activationType) throws ActivationPairNotFoundException;
    ActivationEntity bind(User user, String activationCode, ActivationType activationType);
}
