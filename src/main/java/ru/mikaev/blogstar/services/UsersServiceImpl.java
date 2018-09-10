package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.mikaev.blogstar.dao.NewEmailsRepository;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.NewEmailEntity;
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

    @Autowired
    private NewEmailsRepository newEmailsRepository;

    public User registerUser(UserDto userDto) throws UserAlreadyExistsException, UserEmailAlreadyExistsException{

        if(emailIsBroken(userDto.getEmail())){
            throw new UserEmailAlreadyExistsException("Email is broken !");
        }

        if(usernameIsBroken(userDto.getUsername())){
            throw new UserAlreadyExistsException("Username is broken !");
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
        activationService.bind(user, activationCode, ActivationType.REGISTRATION);

        return user;
    }

    public boolean emailIsBroken(String email){
        Optional<User> userFromDb = usersRepository.findOneByEmail(email);
        Optional<NewEmailEntity> newEmailEntity = newEmailsRepository.findOneByNewEmail(email);

        if(userFromDb.isPresent() || newEmailEntity.isPresent()){
            return true;
        }

        return false;
    }

    public boolean usernameIsBroken(String username){
        Optional<User> userFromDb = usersRepository.findOneByUsername(username);
        if(userFromDb.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public boolean emailIsBrokenNotByMe(String email, User user) {
        Optional<User> userCandidate = usersRepository.findOneByEmail(email);
        Optional<NewEmailEntity> neeCandidate = newEmailsRepository.findOneByNewEmail(email);

        if(userCandidate.isPresent()){
            User userFromDb = userCandidate.get();
            if(userFromDb.getUsername().equals(user.getUsername())){
                return false;
            }
            return true;
        }

        if(neeCandidate.isPresent()){
            NewEmailEntity nee = neeCandidate.get();
            if(nee.getUser().getUsername().equals(user.getUsername())){
                return false;
            }
            return true;
        }

        return false;
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

    @Override
    public User save(User user) {
        return usersRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return usersRepository.findOneByUsername(username);
    }

    @Override
    public Optional<User> findOneByUsernameOrEmail(String username, String email) {
        return usersRepository.findOneByUsernameOrEmail(username, email);
    }
}
