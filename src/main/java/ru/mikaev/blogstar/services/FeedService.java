package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.dto.FeedPostDto;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.FeedForm;

import java.util.List;

public interface FeedService {
    FeedPost addNewPost(User user, FeedForm feedForm);
    List<FeedPost> getGeneralFeedPostsByUser(User user);
    List<FeedPost> getFeedPostsByUser(User user);
}
