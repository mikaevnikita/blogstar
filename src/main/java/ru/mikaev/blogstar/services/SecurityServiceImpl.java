package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.mikaev.blogstar.dao.NewEmailsRepository;
import ru.mikaev.blogstar.entities.ActivationEntity;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.NewEmailEntity;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.EmailAlreadyExistsException;
import ru.mikaev.blogstar.exceptions.InvalidEmailException;
import ru.mikaev.blogstar.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Component
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private NewEmailsRepository newEmailsRepository;

    @Override
    public void changeEmail(User user, String newEmail) throws EmailAlreadyExistsException, InvalidEmailException {
        //so checking/validating checking

        if(user.getEmail().equals(newEmail)){
            return;
        }

        if(usersService.emailIsBrokenNotByMe(newEmail, user)){
            throw new EmailAlreadyExistsException("Email already broken !");
        }

        Optional<NewEmailEntity> emailEntityCandidate = newEmailsRepository.findOneByUser(user);
        if(emailEntityCandidate.isPresent()){
            NewEmailEntity newEmailEntity = emailEntityCandidate.get();
            newEmailsRepository.delete(newEmailEntity);
        }

        String activationCode = mailService.sendChangeEmailMessage(newEmail);
        ActivationEntity activationEntity = activationService.bind(user, activationCode, ActivationType.CHANGE_EMAIL);

        NewEmailEntity newEmailEntity = new NewEmailEntity();
        newEmailEntity.setNewEmail(newEmail);
        newEmailEntity.setUser(user);
        newEmailEntity.setActivationEntity(activationEntity);

        newEmailsRepository.save(newEmailEntity);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        if(newPassword == null || newPassword.length() < 8 || newPassword.length() > 50)
            throw new IllegalArgumentException("Invalid password");
        user.setPassword(passwordEncoder.encode(newPassword));
        usersService.save(user);
    }

    public void confirmEmail(User user){
        if(!user.isActive()) {
            usersService.setActive(user, true);
        }
        else{
            Optional<NewEmailEntity> emailEntityCandidate = newEmailsRepository.findOneByUser(user);
            if(!emailEntityCandidate.isPresent()){
                return;
            }
            NewEmailEntity newEmailEntity = emailEntityCandidate.get();
            user.setEmail(newEmailEntity.getNewEmail());
            usersService.save(user);
            newEmailsRepository.delete(newEmailEntity);
        }
    }

}
