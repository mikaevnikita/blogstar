package ru.mikaev.blogstar.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SignUpForm {
    //twitter length standart
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    //35 - U.K standart
    @NotBlank
    @Size(max = 35)
    private String firstName;

    @NotBlank
    @Size(max = 35)
    private String lastName;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
}
