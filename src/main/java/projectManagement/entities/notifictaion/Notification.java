package projectManagement.entities.notifictaion;

import java.time.LocalDate;

public class Notification {
    private Long id;
    private Long userId;
    private Long assignerId;
    private Long boardId;
    private LocalDate date;
    private NotificationType notificationType;
    //T data;


    public Notification(Long userId, Long assignerId, Long boardId, NotificationType notificationType) {
        this.userId = userId;
        this.assignerId = assignerId;
        this.boardId = boardId;
        this.notificationType = notificationType;
        this.date= LocalDate.now();
    }
}
