package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddTypeDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private Long boardId;
}
