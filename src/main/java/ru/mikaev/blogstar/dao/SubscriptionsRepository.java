package ru.mikaev.blogstar.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.mikaev.blogstar.entities.SubscriptionEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface SubscriptionsRepository extends PagingAndSortingRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findOneByWhoAndOnWhom(User who, User onWhom);
    Page<SubscriptionEntity> findAllByWho(User who, Pageable pageable);
}
