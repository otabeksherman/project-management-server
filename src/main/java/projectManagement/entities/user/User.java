package projectManagement.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;
    private Boolean emailNotify;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.emailNotify = true; //default is true
        this.boards = new HashSet<>();
        this.notifications = new HashSet<>();
    }
}
