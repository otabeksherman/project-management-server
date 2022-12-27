package projectManagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ItemDto {
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    @NotEmpty
    private String status;
    private Long parentItemId;
    @NotNull
    @NotEmpty
    private Long boardId;
    private Long assignedToId;
    private Date dueDate;
    private int importance;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String description;

}
