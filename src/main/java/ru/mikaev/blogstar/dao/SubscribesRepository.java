package ru.mikaev.blogstar.dao;

import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.SubscribeEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.Optional;

public interface SubscribesRepository extends CrudRepository<SubscribeEntity, Long> {
    Optional<SubscribeEntity> findOneByWhoAndOnWhom(User who, User onWhom);
    List<SubscribeEntity> findAllByWho(User who);
}
