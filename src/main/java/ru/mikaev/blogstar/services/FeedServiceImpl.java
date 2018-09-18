package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mikaev.blogstar.dao.FeedPostsRepository;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.FeedForm;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedPostsRepository feedPostsRepository;

    @Override
    public FeedPost addNewPost(User user, FeedForm feedForm) {
        FeedPost feedPost = FeedPost.builder().user(user).content(feedForm.getContent()).build();

        return feedPostsRepository.save(feedPost);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FeedPost> getGeneralFeedPostsByUser(User user) {
        return getFeedPostsByUser(user);//temporary
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FeedPost> getFeedPostsByUser(User user) {
        return feedPostsRepository.findAllByUser(user);
    }
}
