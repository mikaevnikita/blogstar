package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mikaev.blogstar.dao.SubscriptionsRepository;
import ru.mikaev.blogstar.entities.SubscriptionEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {
    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    @Override
    public void doSubscribe(User who, User onWhom) {
        if(isSubscribed(who, onWhom)){
            return;
        }

        SubscriptionEntity subscription = SubscriptionEntity.builder().who(who).onWhom(onWhom).build();
        subscriptionsRepository.save(subscription);
    }

    @Override
    public void doUnsubscribe(User who, User onWhom) {
        Optional<SubscriptionEntity> subscriptionCandidate = subscriptionsRepository.findOneByWhoAndOnWhom(who, onWhom);
        if(!subscriptionCandidate.isPresent()){
            return;
        }
        SubscriptionEntity subscription = subscriptionCandidate.get();
        subscriptionsRepository.delete(subscription);
    }

    @Override
    public boolean isSubscribed(User who, User onWhom) {
        Optional<SubscriptionEntity> subscriptionCandidate = subscriptionsRepository.findOneByWhoAndOnWhom(who, onWhom);
        if(subscriptionCandidate.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<User> getSubscriptionsByUser(User who) {
        return subscriptionsRepository
                .findAllByWho(who)
                .map(SubscriptionEntity::getOnWhom)
                .collect(Collectors.toList());
    }
}
