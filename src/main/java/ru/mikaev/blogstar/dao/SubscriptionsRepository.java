package ru.mikaev.blogstar.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.SubscriptionEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface SubscriptionsRepository extends CrudRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findOneByWhoAndOnWhom(User who, User onWhom);
    Page<SubscriptionEntity> findAllByWho(User who, Pageable pageable);
}
