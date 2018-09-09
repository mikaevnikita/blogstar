package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.Role;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.services.UsersService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        catch (UserAlreadyExistsException ex){
            model.addAttribute("message", "User exists!");
            return "signup";
        }
    }
}
