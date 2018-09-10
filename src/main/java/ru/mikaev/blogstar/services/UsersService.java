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
import ru.mikaev.blogstar.entities.Role;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.forms.ChangeProfileForm;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

public interface UsersService {

    User registerUser(UserDto userDto) throws UserAlreadyExistsException;
    /*
    Cannot be change (username, date of birth)(immutable), (password, email, profile photo) (use services)
     */
    User changeProfileInfo(User user, UserDto newProfileInfo);}
