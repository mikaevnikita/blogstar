package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.entities.SubscribeEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;

public interface SubscribesService {
    void doSubscribe(User who, User onWhom);
    void doUnsubscribe(User who, User onWhom);
    boolean isSubscribed(User who, User onWhom);
    List<SubscribeEntity> getSubscribesByUser(User user);
}
