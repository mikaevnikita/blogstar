package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.exceptions.SavePhotoException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@PropertySources({
        @PropertySource("classpath:ru/mikaev/blogstar/application.properties")
})
public class UsersPhotoServiceLocalFileSystemImpl implements UsersPhotoService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String getDefaultProfilePhotoFilename() {
        return "default-avatar.png";
    }

    @Override
    public void changeProfilePhoto(User user, MultipartFile newProfilePhoto) {
        user.setProfilePhotoFilename(savePhoto(newProfilePhoto));
        usersRepository.save(user);
    }

    @Override
    public void changeProfilePhoto(User user, String profilePhotoFileName) {
        user.setProfilePhotoFilename(profilePhotoFileName);
        usersRepository.save(user);
    }

    @Override
    public String savePhoto(MultipartFile profilePhoto) {
        if(profilePhoto == null || profilePhoto.getOriginalFilename().isEmpty()){
            throw new SavePhotoException("Illegal argument or incorrent path");
        }

        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String filename = generateFilename(profilePhoto.getOriginalFilename());
        try {
            profilePhoto.transferTo(new File(uploadPath + "/" + filename));
            return filename;
        } catch (IOException e) {
            throw new SavePhotoException(e);
        }
    }

    private String generateFilenamePrefix(){
        return UUID.randomUUID().toString();
    }

    private String generateFilename(String originalFilename){
        return generateFilenamePrefix() + "." + originalFilename;
    }
}
