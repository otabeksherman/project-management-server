package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;
import projectManagement.entities.user.UserRole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShareBoardDto {
    @NotNull
    @NotEmpty
    private Long boardId;
    @NotNull
    @NotEmpty
    private String userEmail;
    @NotNull
    @NotEmpty
    private UserRole role;
}
