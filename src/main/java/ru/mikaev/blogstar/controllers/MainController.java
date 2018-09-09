package ru.mikaev.blogstar.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showMain(Authentication authentication, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }
        return "main";
    }
}
