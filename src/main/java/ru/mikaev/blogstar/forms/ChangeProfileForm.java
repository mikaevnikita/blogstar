package ru.mikaev.blogstar.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfileForm {

    //35 - U.K standart
    @NotBlank
    @Size(max = 35)
    @Pattern(regexp = "[A-Za-z]+",
            message = "First name should be contains latin letters")
    private String firstName;

    @NotBlank
    @Size(max = 35)
    @Pattern(regexp = "[A-Za-z]+",
            message = "Last name should be contains latin letters")
    private String lastName;


    private MultipartFile profilePhoto;

    private String aboutMe;
}
