package projectManagement.entities.user;

import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import projectManagement.entities.Status;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserInBoard {
    private static final Logger logger = LogManager.getLogger(UserInBoard.class.getName());

    @EmbeddedId
    private UserInBoardPK id = new UserInBoardPK();

    @ManyToOne()
    @MapsId("boardId")
    @Cascade(CascadeType.ALL)
    private Board board;

    @ManyToOne
    @MapsId("userId")
    @Cascade(CascadeType.ALL)
    private User user;

    @Enumerated
    @Column(name = "role")
    @Cascade(CascadeType.ALL)
    private UserRole role;
}
