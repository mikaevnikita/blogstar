package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikaev.blogstar.dao.ActivationRepository;
import ru.mikaev.blogstar.entities.ActivationEntity;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.ActivationPairNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
public class ActivationServiceUuidImpl implements ActivationService {
    @Autowired
    private ActivationRepository activationRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public String generateActivationCode() {
        return UUID.randomUUID().toString();
    }

    public void doActivate(String code, ActivationType activationType) throws ActivationPairNotFoundException {
        Optional<ActivationEntity> activationCandidate =
                activationRepository.findOneByActivationCodeAndActivationType(code, activationType);
        if(!activationCandidate.isPresent()){
            throw new ActivationPairNotFoundException("Activation pair not found");
        }
        ActivationEntity activationEntity = activationCandidate.get();
        activationRepository.delete(activationEntity);

        executeTaskDueToActivation(activationEntity);
    }

    @Override
    public void bind(User user, String activationCode, ActivationType activationType) {
        ActivationEntity activationEntity = new ActivationEntity();
        activationEntity.setUser(user);
        activationEntity.setActivationCode(activationCode);
        activationEntity.setActivationType(activationType);

        activationRepository.save(activationEntity);
    }

    private void executeTaskDueToActivation(ActivationEntity activationEntity){
        ActivationType activationType = activationEntity.getActivationType();
        switch (activationType){
            case EMAIL:
                usersService.setActive(activationEntity.getUser(), true);
        }
    }


}
