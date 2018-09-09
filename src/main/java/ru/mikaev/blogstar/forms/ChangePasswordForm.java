package ru.mikaev.blogstar.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm {
    @NotBlank
    @Size(min = 8, max = 50)
    private String newPassword;
}
