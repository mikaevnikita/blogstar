package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.mikaev.blogstar.dto.FeedPostDto;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.ProfileNotFoundException;
import ru.mikaev.blogstar.forms.ChangeProfileForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.services.FeedService;
import ru.mikaev.blogstar.services.UsersPhotoService;
import ru.mikaev.blogstar.services.UsersService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ProfileController{

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersPhotoService usersPhotoService;

    @Autowired
    private FeedService feedService;

    @GetMapping("/user/profile")
    String profile(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        UserDto userDto = UserDto.fromUser(user);

        model.addAttribute("user", userDto);

        List<FeedPostDto> posts = feedService
                .getFeedPostsByUser(user)
                .stream()
                .map(FeedPostDto::fromFeedPost)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);

        return "/user/profile/profile";
    }

    @GetMapping("/user/{username}")
    String profile(Authentication authentication, @PathVariable("username") String username, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User sessionedUser = userDetails.getUser();
        if(sessionedUser.getUsername().equals(username)){
            return "redirect:/user/profile";
        }

        Optional<User> userCandidate = usersService.findOneByUsername(username);
        if(!userCandidate.isPresent()){
            throw new ProfileNotFoundException();
        }
        User user = userCandidate.get();
        UserDto userDto = UserDto.fromUser(user);

        model.addAttribute("user", userDto);

        List<FeedPostDto> posts = feedService
                .getFeedPostsByUser(user)
                .stream()
                .map(FeedPostDto::fromFeedPost)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);

        return "/user/profile/strangerProfile";
    }


    @GetMapping("/user/profile/changeProfile")
    String showChangeProfilePage(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());
        model.addAttribute("user", userDto);
        model.addAttribute("form", userDto);
        return "/user/profile/changeProfile";
    }

    @PostMapping("/user/profile/changeProfile")
    String changeProfileInfo(Authentication authentication, @Valid ChangeProfileForm form, BindingResult bindingResult, Model model) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            UserDto userDto = UserDto.fromUser(user);
            userDto.setFirstName(form.getFirstName());
            userDto.setLastName(form.getLastName());
            userDto.setAboutMe(form.getAboutMe());

            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form", form);
            model.addAttribute("user", userDto);
            return "/user/profile/changeProfile";
        }

        usersService.changeFirstNameLastNameAboutMe(user, form);

        MultipartFile profilePhoto = form.getProfilePhoto();
        if(profilePhoto != null && !profilePhoto.getOriginalFilename().isEmpty()){
            usersPhotoService.changeProfilePhoto(user, profilePhoto);
        }
        return "redirect:/user/profile";
    }

}