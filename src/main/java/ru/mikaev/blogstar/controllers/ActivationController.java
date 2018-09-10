package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.exceptions.ActivationPairNotFoundException;
import ru.mikaev.blogstar.exceptions.ActivationServiceException;
import ru.mikaev.blogstar.services.ActivationService;

@Controller
public class ActivationController {
    @Autowired
    private ActivationService activationService;

    @GetMapping("/activate/{activation-type}/{code}")
    public String activate(@PathVariable("activation-type") ActivationType activationType, @PathVariable("code") String code, Model model){

        try{
            activationService.doActivate(code, activationType);
            model.addAttribute("message", "Successfully activated");
        }
        catch (ActivationPairNotFoundException ex){
            model.addAttribute("message", "Activation code is not found or another error");
        }

        return "activate";
    }
}
