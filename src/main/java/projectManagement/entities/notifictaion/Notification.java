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
}
