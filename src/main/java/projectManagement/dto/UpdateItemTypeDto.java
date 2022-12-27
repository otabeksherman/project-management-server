package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class UpdateItemTypeDto {
    @NotNull
    @NotEmpty
    private Long itemId;
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    @NotEmpty
    private Long boardId;
}
