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
    private boolean isGeneralAccount;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;
    private Boolean emailNotify;

    public User(String email, String password) {
        this.isGeneralAccount=true;
        this.email = email;
        this.password = password;
        this.emailNotify = true; //default is true
        this.boards = new HashSet<>();
        this.notifications = new HashSet<>();
    }
    public User(String email) {
        this.isGeneralAccount=false;
        this.email = email;
        this.password = email;
        this.emailNotify = true; //default is true
        this.boards = new HashSet<>();
        this.notifications = new HashSet<>();
    }
}
