package ru.mikaev.blogstar.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;

import java.util.List;

public interface FeedPostsRepository extends CrudRepository<FeedPost, Long> {
    Page<FeedPost> findAll(Pageable pageable);
    Page<FeedPost> findAllByUser(User user, Pageable pageable);
}
