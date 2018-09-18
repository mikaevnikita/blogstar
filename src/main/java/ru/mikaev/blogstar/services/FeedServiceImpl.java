package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<FeedPost> getGeneralFeedPostsByUserSortedByDateTime(User user) {
        return Stream
                .concat(subscriptionsService
                        .getSubscriptionsByUser(user)
                        .stream()
                        .map(this::getFeedPostsByUser)
                                .flatMap(List::stream),
                        getFeedPostsByUser(user)
                                .stream())
                .parallel()
                .sorted(
                        new Comparator<FeedPost>() {
                            @Override
                            public int compare(FeedPost o1, FeedPost o2) {
                                //reverseOrder
                                return -1 * o1.getDateTime().compareTo(o2.getDateTime());
                            }
                        })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FeedPost> getFeedPostsByUser(User user) {
        return feedPostsRepository.findAllByUser(user);
    }
}
