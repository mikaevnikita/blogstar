package ru.mikaev.blogstar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mikaev.blogstar.entities.NewEmailEntity;
import ru.mikaev.blogstar.entities.NewPasswordEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface NewPasswordsRepository extends JpaRepository<NewPasswordEntity, Long> {
    Optional<NewPasswordEntity> findOneByUser(User user);
}
