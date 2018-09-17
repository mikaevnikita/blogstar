package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.NewEmailEntity;
import ru.mikaev.blogstar.entities.Role;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.forms.ChangeProfileForm;
import ru.mikaev.blogstar.forms.SignUpForm;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

public interface UsersService {

    User registerUser(SignUpForm signUpForm) throws UserAlreadyExistsException;

    /*
    Cannot be change (username, date of birth)(immutable), (password, email, profile photo) (use services)
     */
    User changeFirstNameLastNameAboutMe(User user, ChangeProfileForm changeProfileForm);

    void setActive(User user, boolean active);

    User save(User user);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByUsernameOrEmail(String username, String email);

    boolean emailIsBroken(String email);

    boolean usernameIsBroken(String username);

    boolean emailIsBrokenNotByMe(String email, User user);
}


