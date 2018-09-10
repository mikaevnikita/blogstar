package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.services.ActivationService;

@Controller
public class ActivationController {
    @Autowired
    private ActivationService activationService;

    @GetMapping("/activate/{activation-type}/{code}")
    public String activate(@PathVariable("activation-type") ActivationType activationType, @PathVariable("code") String code, Model model){
        boolean isActivated = activationService.doActivate(code, activationType);
        if(isActivated){
            model.addAttribute("message", "User successfully activated");
        }
        else{
            model.addAttribute("message", "Activation code is not found or another error");
        }

        return "signin";
    }
}
