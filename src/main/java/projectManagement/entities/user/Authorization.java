package projectManagement.entities.user;

import org.springframework.data.annotation.Id;
import projectManagement.entities.board.Board;

import javax.persistence.*;

@Entity
@Table(name = "authorized_users")
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated
    @Column(name = "permission")
    private UserRole permission;

    public Authorization() {
    }

    public Authorization(Board board, User user, UserRole permission) {
        this.board = board;
        this.user = user;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public Board getboard() {
        return board;
    }

    public User getUser() {
        return user;
    }

    public UserRole getPermission() {
        return permission;
    }

    public void setPermission(UserRole permission) {
        this.permission = permission;
    }
}
