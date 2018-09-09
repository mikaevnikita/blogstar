package ru.mikaev.blogstar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByActivationCode(String code);
}
