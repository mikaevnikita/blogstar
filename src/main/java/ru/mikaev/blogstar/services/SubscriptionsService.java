package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.entities.SubscriptionEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.stream.Stream;

public interface SubscriptionsService {
    void doSubscribe(User who, User onWhom);
    void doUnsubscribe(User who, User onWhom);
    boolean isSubscribed(User who, User onWhom);
    List<User> getSubscriptionsByUser(User user);
}
