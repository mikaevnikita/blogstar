package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mikaev.blogstar.dao.FeedPostsRepository;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.FeedForm;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedPostsRepository feedPostsRepository;

    @Autowired
    private SubscriptionsService subscriptionsService;

    @Override
    public FeedPost addNewPost(User user, FeedForm feedForm) {
        FeedPost feedPost = FeedPost.builder().user(user).content(feedForm.getContent()).dateTime(LocalDateTime.now()).build();

        return feedPostsRepository.save(feedPost);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<FeedPost> getGeneralFeedPostsByUser(User user, Pageable pageable) {
        return getFeedPostsByUser(user, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<FeedPost> getFeedPostsByUser(User user, Pageable pageable) {
        return feedPostsRepository.findAllByUser(user, pageable);
    }
}
