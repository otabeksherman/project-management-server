package projectManagement.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean githubAccount;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Board> boards;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;
    @Column(nullable = false)
    private Boolean emailNotify;
    @Column(nullable = false)
    private Boolean popNotify;

    @ElementCollection
    @CollectionTable(name="notification_type")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn
    @Column
    private Map<NotificationType,Boolean> notificationTypeSettings;

    public User(String email, String password) {
        this.githubAccount = false;
        this.email = email;
        this.password = password;
        this.emailNotify = true;
        this.popNotify = true;
        this.boards = new HashSet<>();
        this.notifications = new HashSet<>();
        initNotifications();
    }

    public User(String email) {
        this(email,email);
    }

    private void initNotifications(){
        notificationTypeSettings= new EnumMap<NotificationType,Boolean>(NotificationType.class);
        for(NotificationType notification: NotificationType.values()){
            notificationTypeSettings.put(notification,true);
        }
    }

    public void updateNotificationTypeSetting(NotificationType notificationType, Boolean update){
        notificationTypeSettings.put(notificationType,update);
    }

    public boolean isGithubAccount() {
        return githubAccount;
    }

    public void addNotifications(Notification notification) {
        this.notifications.add(notification);
    }
}
