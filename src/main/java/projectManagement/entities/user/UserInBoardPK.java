package projectManagement.entities.user;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Setter
public class UserInBoardPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long boardId;
}
