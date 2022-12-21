package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;
import projectManagement.util.annotation.PasswordMatches;
import projectManagement.util.annotation.ValidEmail;
import projectManagement.util.annotation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
public class RegistrationDto {
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;

    @ValidPassword
    @NotNull
    @NotEmpty
    private String matchingPassword;
}


