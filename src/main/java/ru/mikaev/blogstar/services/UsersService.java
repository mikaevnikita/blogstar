package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDto userDto) throws UserAlreadyExistsException{
        Optional<User> userFromDb = usersRepository.findOneByUsername(userDto.getUsername());

        if(userFromDb.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User
                .builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .dateOfBirth(userDto.getDateOfBirth())
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .profilePhotoFilename("default-avatar.png")
                .build();

        return usersRepository.save(user);
    }


    /*
    Cannot be change (username, date of birth)(immutable), (password, email, profile photo) (use services)
     */
    public User changeProfileInfo(User user, UserDto newProfileInfo){
        user.setFirstName(newProfileInfo.getFirstName());
        user.setLastName(newProfileInfo.getLastName());

        return usersRepository.save(user);
    }
}
