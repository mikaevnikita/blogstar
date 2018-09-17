package ru.mikaev.blogstar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.NewEmailEntity;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface NewEmailsRepository extends CrudRepository<NewEmailEntity, Long> {
    Optional<NewEmailEntity> findOneByUser(User user);
    Optional<NewEmailEntity> findOneByNewEmail(String newEmail);
}
