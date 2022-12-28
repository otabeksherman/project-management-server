package projectManagment.controller.entites.user;
import org.junit.jupiter.api.Test;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user = new User("test@example.com", "Aa123456!");


    @Test
    void testConstructor() {
        assertFalse(user.isGithubAccount());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Aa123456!", user.getPassword());
        assertTrue(user.getEmailNotify());
        assertTrue(user.getPopNotify());
        assertNotNull(user.getBoards());
        assertNotNull(user.getNotifications());
    }

    @Test
    void testUpdateNotificationTypeSetting() {
        user.updateNotificationTypeSetting(NotificationType.DELETED, false);
        assertFalse(user.getNotificationTypeSettings().get(NotificationType.DELETED));
    }

//    @Test
//    void testAddNotifications() {
//        User userAssigner = new User("test2@example.com", "Bb123456!");
//        Board board=new Board("board1",userAssigner);
//        Notification notification = new Notification.Builder()
//                .user(user).assigner(userAssigner).board(board).date(LocalDate.now())
//                .notificationType(NotificationType.ASSIGN_TO_ME).build();
//        user.addNotifications(notification);
//        assertTrue(user.getNotifications().contains(notification));
//    }
}
