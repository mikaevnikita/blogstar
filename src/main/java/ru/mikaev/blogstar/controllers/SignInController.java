package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mikaev.blogstar.dao.UserRepository;

@Controller
public class SignInController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/signin")
    public String login(@RequestParam(name = "error", required = false) String error, Authentication authentication, Model model){
        if(authentication != null){
            return "redirect:/profile";
        }
        Boolean errorStatus = Boolean.valueOf(error);
        if(errorStatus != null && errorStatus != false){
            model.addAttribute("error", true);
        }
        return "signin";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/signin";
    }
}
