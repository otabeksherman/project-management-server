package projectManagement.entities.notifictaion;

import java.time.LocalDate;

public class Notification {
    Long id;
    Long userId;
    Long assignerId;
    Long boardId;
    LocalDate date;
    NotificationType notificationType;
    //T data;


    public Notification(Long userId, Long assignerId, Long boardId, NotificationType notificationType) {
        this.userId = userId;
        this.assignerId = assignerId;
        this.boardId = boardId;
        this.notificationType = notificationType;
        this.date= LocalDate.now();
    }
}
