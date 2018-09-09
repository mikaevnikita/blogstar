package ru.mikaev.blogstar.services;

import org.springframework.web.multipart.MultipartFile;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.SavePhotoException;

import java.io.IOException;

public interface UsersPhotoService {
    String getDefaultProfilePhotoFilename();
    void changeProfilePhoto(User user, MultipartFile newProfilePhoto);
    void changeProfilePhoto(User user, String profilePhotoFileName);
    String savePhoto(MultipartFile photo) throws SavePhotoException;
}
