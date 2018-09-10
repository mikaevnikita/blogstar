package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.SecurityServiceException;
import ru.mikaev.blogstar.forms.ChangeEmailForm;
import ru.mikaev.blogstar.forms.ChangePasswordForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.services.SecurityService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class ChangeEmailController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/user/profile/changeEmail")
    String showChangeEmailPage(){
        return "user/profile/changeEmail";
    }

    @PostMapping("/user/profile/changeEmail")
    String changeEmail(Authentication authentication, @Valid ChangeEmailForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", form);
            return "/user/profile/changeEmail";
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        try {
            securityService.changeEmail(user, form.getNewEmail());
            model.addAttribute("message", "Check your mail");
            return "/user/profile";
        }
        catch (SecurityServiceException ex){
            model.addAttribute("newEmailErrors", Collections.singletonList(ex.getMessage()));
            return "/user/profile/changeEmail";
        }
    }
}
