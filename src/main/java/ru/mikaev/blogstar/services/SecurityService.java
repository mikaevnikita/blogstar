package ru.mikaev.blogstar.services;

import ru.mikaev.blogstar.entities.User;

public interface SecurityService {
    void changeEmail(User user, String newEmail);
    void changePassword(User user, String newPassword);
}
