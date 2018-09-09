package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.Role;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    private UsersRepository userRepository;

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
    public String addUser(Authentication authentication, @Valid UserDto form, BindingResult bindingResult, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }

        if(bindingResult.hasErrors()){
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", form);
            return "signup";
        }

        Optional<User> userFromDb = userRepository.findOneByUsername(form.getUsername());

        if(userFromDb.isPresent()){
            model.addAttribute("message", "User exists!");
            return "signup";
        }

        User user = User
                .builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .dateOfBirth(form.getDateOfBirth())
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .build();

        userRepository.save(user);

        return "redirect:/signin";
    }
}
