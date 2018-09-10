package ru.mikaev.blogstar.controllers;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.exceptions.UsersServiceException;
import ru.mikaev.blogstar.services.UsersService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/signup")
    public String signup(Authentication authentication, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(Authentication authentication, @Valid UserDto userDto, BindingResult bindingResult, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }

        if(bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", userDto);
            return "signup";
        }

        try {
            usersService.registerUser(userDto);
            model.addAttribute("message", "Please check your email to activate account");
            return "signin";
        }
        catch (UsersServiceException ex){
            model.addAttribute("message", ex.getMessage());
            return "signup";
        }
    }
}
