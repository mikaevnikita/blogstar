package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mikaev.blogstar.services.UsersService;

@Controller
public class MailController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable("code") String code, Model model){
        boolean isActivated = usersService.activateUser(code);
        if(isActivated){
            model.addAttribute("message", "User successfully activated");
        }
        else{
            model.addAttribute("message", "Activation code is not found");
        }

        return "signin";
    }
}
