package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.ChangePasswordForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.services.SecurityService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;

@Controller
public class ChangePasswordController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/user/profile/changePassword")
    String showChangePasswordPage(){
        return "user/profile/changePassword";
    }

    @PostMapping("/user/profile/changePassword")
    String changePassword(Authentication authentication, @Valid ChangePasswordForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", form);
            return "/user/profile/changePassword";
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        securityService.changePassword(user, form.getNewPassword());

        model.addAttribute("message", "Check your mail");
        model.addAttribute("user", user);
        return "/user/profile/profile";
    }
}
