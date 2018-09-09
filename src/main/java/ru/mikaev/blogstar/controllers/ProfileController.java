package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.ChangePasswordForm;
import ru.mikaev.blogstar.forms.ChangeProfileForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping("/user/profile")
    String profile(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());
        model.addAttribute("user", userDto);
        return "/user/profile/profile";
    }

    @GetMapping("/user/profile/changeProfile")
    String showChangeProfilePage(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());

        model.addAttribute("form", userDto);
        return "/user/profile/changeProfile";
    }

    @PostMapping("/user/profile")
    String changeProfileInfo(Authentication authentication, @Valid ChangeProfileForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", form);
            return "/user/profile/changeProfile";
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        usersRepository.save(user);

        return "redirect:/user/profile";
    }

}