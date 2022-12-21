package projectManagement.entities.notifictaion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "assigner_id", nullable = false)
    private User assigner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
    private LocalDate date;
    private NotificationType notificationType;

    public static class Builder{
        private User user;
        private User assigner;
        private Board board;
        private LocalDate date;
        private NotificationType notificationType;

        public Builder() {
        }

        public Builder user(User user){
            this.user=user;
            return this;
        }
        public Builder assigner(User assigner){
            this.assigner=assigner;
            return this;
        }
        public Builder board(Board board){
            this.board=board;
            return this;
        }
        public Builder date(LocalDate date){
            this.date=date;
            return this;
        }
        public Builder notificationType(NotificationType notificationType){
            this.notificationType=notificationType;
            return this;
        }

        public Notification build(){
            return new Notification(this);
        }
    }

    public Notification(Builder builder){
        this.user = builder.user;
        this.assigner = builder.assigner;
        this.board = builder.board;
        this.date = builder.date;
        this.notificationType = builder.notificationType;
    }




}
