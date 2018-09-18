package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mikaev.blogstar.dto.FeedPostDto;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.FeedForm;
import ru.mikaev.blogstar.services.FeedService;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping("/user/feed")
    String showFeed(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        List<FeedPostDto> posts = feedService
                .getGeneralFeedPostsByUserSortedByDateTime(user)
                .stream()
                .map(FeedPostDto::fromFeedPost)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "/user/feed";
    }

    @PostMapping("/user/feed")
    String add(Authentication authentication, @Valid FeedForm form, BindingResult bindingResult, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        List<FeedPost> posts = Collections.emptyList();

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("posts", posts);
            return "/user/feed";
        }

        feedService.addNewPost(user, form);

        return "redirect:/user/feed";
    }
}
