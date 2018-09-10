package ru.mikaev.blogstar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mikaev.blogstar.entities.ActivationEntity;
import ru.mikaev.blogstar.entities.ActivationType;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface ActivationRepository extends JpaRepository<ActivationEntity, Long> {
    Optional<ActivationEntity> findOneByActivationCodeAndActivationType(String activationCode, ActivationType activationType);
}
