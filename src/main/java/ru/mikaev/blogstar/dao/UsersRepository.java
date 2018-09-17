package ru.mikaev.blogstar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByUsernameOrEmail(String username, String email);
    Optional<User> findOneByEmail(String email);
}