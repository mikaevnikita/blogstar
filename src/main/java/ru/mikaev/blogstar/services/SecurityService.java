package ru.mikaev.blogstar.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.EmailAlreadyExistsException;
import ru.mikaev.blogstar.exceptions.InvalidEmailException;

import java.util.List;

public interface SecurityService {
    void changeEmail(User user, String newEmail) throws EmailAlreadyExistsException, InvalidEmailException;
    void confirmEmail(User user);
    void changePassword(User user, String newPassword);
    void confirmPassword(User user);
}
