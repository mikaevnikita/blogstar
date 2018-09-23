package ru.mikaev.blogstar.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mikaev.blogstar.entities.SubscriptionEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.stream.Stream;

public interface SubscriptionsService {
    void doSubscribe(User who, User onWhom);
    void doUnsubscribe(User who, User onWhom);
    boolean isSubscribed(User who, User onWhom);
    Page<User> getSubscriptionsByUser(User user, Pageable pageable);
}
