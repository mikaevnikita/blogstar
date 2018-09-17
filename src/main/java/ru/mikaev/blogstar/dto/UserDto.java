package ru.mikaev.blogstar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.mikaev.blogstar.entities.User;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth;

    private String profilePhotoFilename;

    private String aboutMe;

    private String email;

    public static UserDto fromUser(User user){
        return UserDto
                .builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .profilePhotoFilename(user.getProfilePhotoFilename())
                .aboutMe(user.getAboutMe())
                .email(user.getEmail())
                .build();
    }
}
