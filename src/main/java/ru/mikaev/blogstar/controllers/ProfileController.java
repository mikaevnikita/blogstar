package ru.mikaev.blogstar.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.dto.UserDto;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    String profile(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());
        model.addAttribute("user", userDto);
        return "profile";
    }
}