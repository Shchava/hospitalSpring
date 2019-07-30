package ua.training.hospital.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ua.training.hospital.controller.utils.EmailValidation;
import ua.training.hospital.controller.utils.NameValidation;
import ua.training.hospital.controller.utils.PasswordMatches;
import ua.training.hospital.controller.utils.PasswordValidation;
import ua.training.hospital.entity.enums.UserRole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches(password = "password",confirmPassword = "confirmPassword")
public class UserDTO {
    @NotEmpty(message = "{name.empty}")
    @NameValidation
    private String name;

    @NotEmpty(message = "{surname.empty}")
    @NameValidation(message = "{surname.validation}")
    private String surname;

    @NotEmpty(message = "{patronymic.empty}")
    @NameValidation(message = "{patronymic.validation}")
    private String patronymic;



    @NotEmpty(message = "{email.empty}")
    @EmailValidation
    private String email;

    @NotEmpty(message = "{password.empty}")
    @PasswordValidation
    private String password;

    @NotEmpty(message = "{confirmPassword.empty}")
    private String confirmPassword;

    @NotNull(message = "{role.null}")
    private UserRole role;
}
