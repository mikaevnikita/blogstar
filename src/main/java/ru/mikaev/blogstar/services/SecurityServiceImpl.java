package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.entities.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Component
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changeEmail(User user, String newEmail) {
        //stub
    }

    @Override
    public void changePassword(User user, String newPassword) {
        if(newPassword == null || newPassword.length() < 8 || newPassword.length() > 50)
            throw new IllegalArgumentException("Invalid password");
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }
}
