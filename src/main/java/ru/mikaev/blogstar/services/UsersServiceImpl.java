package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.Role;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.exceptions.UserEmailAlreadyExistsException;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public User registerUser(UserDto userDto) throws UserAlreadyExistsException, UserEmailAlreadyExistsException{
        Optional<User> userFromDb = usersRepository.findOneByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());

        if(userFromDb.isPresent()){
            User user = userFromDb.get();

            if(user.getUsername().equals(userDto.getUsername())) {
                throw new UserAlreadyExistsException("User already exists !");
            }
            else
                throw new UserEmailAlreadyExistsException("User with this email already exists !");
        }

        User user = User
                .builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .dateOfBirth(userDto.getDateOfBirth())
                .active(false)
                .roles(Collections.singleton(Role.USER))
                .profilePhotoFilename("default-avatar.png")
                .aboutMe(StringUtils.isEmpty(userDto.getAboutMe()) ? "" : userDto.getAboutMe())
                .email(userDto.getEmail())
                .build();

        usersRepository.save(user);

        String activationCode = mailService.sendRegisterMessage(user.getEmail(), user.getUsername());
        activationService.bind(user, activationCode, ActivationType.EMAIL);

        return user;
    }


    /*
    Cannot be change (username, date of birth)(immutable), (password, email, profile photo) (use services)
     */
    public User changeProfileInfo(User user, UserDto newProfileInfo){
        user.setFirstName(newProfileInfo.getFirstName());
        user.setLastName(newProfileInfo.getLastName());
        user.setAboutMe(newProfileInfo.getAboutMe());

        return usersRepository.save(user);
    }

    @Override
    public void setActive(User user, boolean active) {
        user.setActive(active);
        usersRepository.save(user);
    }
}
