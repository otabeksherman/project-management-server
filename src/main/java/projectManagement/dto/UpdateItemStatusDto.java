package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateItemStatusDto {
    @NotNull
    @NotEmpty
    private Long itemId;
    @NotNull
    @NotEmpty
    private String status;
    @NotNull
    @NotEmpty
    private Long boardId;
}
