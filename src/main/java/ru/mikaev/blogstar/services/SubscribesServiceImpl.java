package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikaev.blogstar.dao.SubscribesRepository;
import ru.mikaev.blogstar.entities.SubscribeEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public class SubscribesServiceImpl implements SubscribesService {
    @Autowired
    private SubscribesRepository subscribesRepository;

    @Override
    public void doSubscribe(User who, User onWhom) {
        if(isSubscribed(who, onWhom)){
            return;
        }

        SubscribeEntity subscription = SubscribeEntity.builder().who(who).onWhom(onWhom).build();
        subscribesRepository.save(subscription);
    }

    @Override
    public void doUnsubscribe(User who, User onWhom) {
        Optional<SubscribeEntity> subscriptionCandidate = subscribesRepository.findOneByWhoAndOnWhom(who, onWhom);
        if(!subscriptionCandidate.isPresent()){
            return;
        }
        SubscribeEntity subscription = subscriptionCandidate.get();
        subscribesRepository.delete(subscription);
    }

    @Override
    public boolean isSubscribed(User who, User onWhom) {
        Optional<SubscribeEntity> subscriptionCandidate = subscribesRepository.findOneByWhoAndOnWhom(who, onWhom);
        if(subscriptionCandidate.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public List<SubscribeEntity> getSubscribesByUser(User who) {
        return subscribesRepository.findAllByWho(who);
    }
}
