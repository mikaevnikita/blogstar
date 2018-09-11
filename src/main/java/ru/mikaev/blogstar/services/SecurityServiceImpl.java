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
import ru.mikaev.blogstar.dao.NewPasswordsRepository;
import ru.mikaev.blogstar.entities.*;
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

    @Autowired
    private NewPasswordsRepository newPasswordsRepository;

    @Override
    public void changeEmail(User user, String newEmail) throws EmailAlreadyExistsException, InvalidEmailException {
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
        Optional<NewPasswordEntity> passwordEntityCandidate = newPasswordsRepository.findOneByUser(user);
        if(passwordEntityCandidate.isPresent()){
            NewPasswordEntity newPasswordEntity = passwordEntityCandidate.get();
            newPasswordsRepository.delete(newPasswordEntity);
        }

        String activationCode = mailService.sendChangePasswordMessage(user.getEmail());
        ActivationEntity activationEntity = activationService.bind(user, activationCode, ActivationType.CHANGE_PASSWORD);

        NewPasswordEntity newPasswordEntity = new NewPasswordEntity();
        newPasswordEntity.setNewPassword(passwordEncoder.encode(newPassword));
        newPasswordEntity.setUser(user);
        newPasswordEntity.setActivationEntity(activationEntity);

        newPasswordsRepository.save(newPasswordEntity);
    }

    @Override
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

    @Override
    public void confirmPassword(User user) {
        Optional<NewPasswordEntity> passwordEntityCandidate = newPasswordsRepository.findOneByUser(user);
        if(!passwordEntityCandidate.isPresent()){
            return;
        }
        NewPasswordEntity newPasswordEntity = passwordEntityCandidate.get();
        user.setPassword(newPasswordEntity.getNewPassword());
        usersService.save(user);
        newPasswordsRepository.delete(newPasswordEntity);
    }
}
