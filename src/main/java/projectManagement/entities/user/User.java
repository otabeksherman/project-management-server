package projectManagement.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;

import javax.persistence.*;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
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
    private boolean githubAccount;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Notification> notifications;


    @ElementCollection
    @CollectionTable(name = "notification_type_by_email")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn
    @Column
    private Map<NotificationType, Boolean> notificationTypeSettingsEmail;

    @ElementCollection
    @CollectionTable(name = "notification_type_by_popup")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn
    @Column
    private Map<NotificationType, Boolean> notificationTypeSettingsPopup;

    public User(String email, String password) {
        this.githubAccount = false;
        this.email = email;
        this.password = password;

        this.boards = new HashSet<>();
        this.notifications = new HashSet<>();
        initNotifications();
    }

    public User(String email) {
        this(email, email);
    }

    /**
     * active all user notification type setting to true (on email and popup)
     */
    private void initNotifications() {
        notificationTypeSettingsEmail = new EnumMap<NotificationType, Boolean>(NotificationType.class);
        notificationTypeSettingsPopup = new EnumMap<NotificationType, Boolean>(NotificationType.class);
        for (NotificationType notification : NotificationType.values()) {
            notificationTypeSettingsEmail.put(notification, true);
            notificationTypeSettingsPopup.put(notification, true);
        }
    }

    /**
     * change notification type state (on email)
     *
     * @param notificationType
     * @param update
     */
    public void updateNotificationTypeSettingByEmail(NotificationType notificationType, Boolean update) {
        notificationTypeSettingsEmail.put(notificationType, update);
    }

    /**
     * change notification type state (on popup)
     *
     * @param notificationType
     * @param update
     */
    public void updateNotificationTypeSettingByPopup(NotificationType notificationType, Boolean update) {
        notificationTypeSettingsPopup.put(notificationType, update);
    }

    public boolean isGithubAccount() {
        return githubAccount;
    }

    public void addNotifications(Notification notification) {
        this.notifications.add(notification);
    }
}
