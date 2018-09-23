package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.dto.FeedPostDto;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.ProfileNotFoundException;
import ru.mikaev.blogstar.forms.FeedForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.services.FeedService;
import ru.mikaev.blogstar.services.UsersService;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class FeedController {
    @Autowired
    private FeedService feedService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/user/feed")
    String showFeed(Authentication authentication, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Page<FeedPost> page = feedService.getGeneralFeedPostsByUser(user, pageable);
        Page<FeedPostDto> pageDto = ControllerUtils.transformToPageOfFeedPostDto(page);

        model.addAttribute("page", pageDto);
        model.addAttribute("url", "/user/feed");
        return "/user/feed/feed";
    }

    @PostMapping("/user/feed")
    String add(Authentication authentication, @Valid FeedForm form, BindingResult bindingResult, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            return "redirect:/user/feed";
        }

        feedService.addNewPost(user, form);

        return "redirect:/user/feed";
    }

    @GetMapping("/user/feed/{username}")
    String showFeed(Authentication authentication, @PathVariable("username") String username, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Optional<User> userCandidate = usersService.findOneByUsername(username);
        if(!userCandidate.isPresent()){
            throw new ProfileNotFoundException();
        }
        User user = userCandidate.get();
        UserDto userDto = UserDto.fromUser(user);

        Page<FeedPost> page = feedService.getGeneralFeedPostsByUser(user, pageable);
        Page<FeedPostDto> pageDto = ControllerUtils.transformToPageOfFeedPostDto(page);
        model.addAttribute("page", pageDto);
        model.addAttribute("url", String.format("/user/feed/%s", username));
        model.addAttribute("user", userDto);

        return "/user/feed/strangerProfile";
    }
}
