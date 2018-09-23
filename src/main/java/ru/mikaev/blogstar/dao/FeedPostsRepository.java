package ru.mikaev.blogstar.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;

public interface FeedPostsRepository extends PagingAndSortingRepository<FeedPost, Long> {
    Page<FeedPost> findAll(Pageable pageable);
    Page<FeedPost> findAllByUser(User user, Pageable pageable);
}
