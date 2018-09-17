package ru.mikaev.blogstar.forms;

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
public class SignUpForm {
    //twitter length standart
    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^[A-Za-z0-9]+(?:[_][A-Za-z0-9]+)*$",
            message = "Username can contains latin letters, numbers and underscores")
    private String username;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

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

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    private String profilePhotoFilename;

    @Size(max = 200)
    private String aboutMe;

    @Email
    private String email;
}
