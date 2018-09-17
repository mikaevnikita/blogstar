package ru.mikaev.blogstar.dao;

import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;

import java.util.List;

public interface FeedPostsRepository extends CrudRepository<FeedPost, Long> {
    List<FeedPost> findAllByUser(User user);
}
