package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikaev.blogstar.dao.ActivationRepository;
import ru.mikaev.blogstar.entities.ActivationEntity;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;
import java.util.UUID;

@Service
public class ActivationServiceUuidImpl implements ActivationService {
    @Autowired
    private ActivationRepository activationRepository;

    @Override
    public String generateActivationCode() {
        return UUID.randomUUID().toString();
    }

    public boolean doActivate(String code, ActivationType activationType){
        Optional<ActivationEntity> activationCandidate =
                activationRepository.findOneByActivationCodeAndActivationType(code, activationType);
        if(!activationCandidate.isPresent()){
            return false;
        }
        ActivationEntity activationEntity = activationCandidate.get();
        activationRepository.delete(activationEntity);

        return true;
    }

    @Override
    public void bind(User user, String activationCode, ActivationType activationType) {
        ActivationEntity activationEntity = new ActivationEntity();
        activationEntity.setUser(user);
        activationEntity.setActivationCode(activationCode);
        activationEntity.setActivationType(activationType);

        activationRepository.save(activationEntity);
    }


}
