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

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public User registerUser(UserDto userDto) throws UserAlreadyExistsException{
        Optional<User> userFromDb = usersRepository.findOneByUsername(userDto.getUsername());

        if(userFromDb.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }

        String activationCode = mailService.sendRegisterMessage(userDto.getEmail(), userDto.getUsername());

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
                .aboutMe(StringUtils.isEmpty(userDto.getAboutMe()) ? "" : userDto.getAboutMe())
                .email(userDto.getEmail())
                .activationCode(activationCode)
                .build();

        return usersRepository.save(user);
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

    public boolean activateUser(String code){
        Optional<User> userCandidate = usersRepository.findOneByActivationCode(code);
        if(!userCandidate.isPresent()){
            return false;
        }
        User user = userCandidate.get();
        user.setActivationCode(null);
        usersRepository.save(user);

        return true;
    }
}
