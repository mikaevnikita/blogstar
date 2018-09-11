package ru.mikaev.blogstar.controllers;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.exceptions.UserAlreadyExistsException;
import ru.mikaev.blogstar.exceptions.UsersServiceException;
import ru.mikaev.blogstar.services.CaptchaService;
import ru.mikaev.blogstar.services.UsersService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@PropertySources({
        @PropertySource("classpath:ru/mikaev/blogstar/application.properties")
})
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/signup")
    public String signup(Authentication authentication, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(Authentication authentication, @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid UserDto userDto, BindingResult bindingResult, Model model){
        if(authentication != null){
            return "redirect:/user/profile";
        }

        boolean captchaIsValid = captchaService.validate(captchaResponse);
        if(!captchaIsValid){
            model.addAttribute("captchaErrors", Collections.singletonList("Fill captcha"));
        }

        if(bindingResult.hasErrors() || !captchaIsValid){
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
