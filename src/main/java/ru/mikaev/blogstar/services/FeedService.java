package ru.mikaev.blogstar.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.FeedForm;

public interface FeedService {
    FeedPost addNewPost(User user, FeedForm feedForm);
    Page<FeedPost> getGeneralFeedPostsByUser(User user, Pageable pageable);
    Page<FeedPost> getFeedPostsByUser(User user, Pageable pageable);
}
