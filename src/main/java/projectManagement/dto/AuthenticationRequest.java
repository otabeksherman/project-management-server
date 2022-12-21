package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;
import projectManagement.util.annotation.ValidEmail;
import projectManagement.util.annotation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationRequest {
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;
}
