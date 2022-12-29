package projectManagment.entites.notification;

import org.junit.jupiter.api.Test;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationTest {
    @Test
    void testGetUser() {
        // Create a mock user object
        User user = mock(User.class);

        // Create a Notification object with the mock user
        Notification notification = new Notification();
        notification.setUser(user);

        // Verify that the getUser() method returns the expected user
        assertEquals(user, notification.getUser());
    }

    @Test
    void testGetAssigner() {
        // Create a mock user object
        User assigner = mock(User.class);

        // Create a Notification object with the mock assigner
        Notification notification = new Notification();
        notification.setAssigner(assigner);

        // Verify that the getAssigner() method returns the expected assigner
        assertEquals(assigner, notification.getAssigner());
    }

    @Test
    void testGetBoard() {
        // Create a mock board object
        Board board = mock(Board.class);

        // Create a Notification object with the mock board
        Notification notification = new Notification();
        notification.setBoard(board);

        // Verify that the getBoard() method returns the expected board
        assertEquals(board, notification.getBoard());
    }

    @Test
    void testGetDate() {
        // Create a LocalDate object for the date
        LocalDate date = LocalDate.now();

        // Create a Notification object with the date
        Notification notification = new Notification();
        notification.setDate(date);

        // Verify that the getDate() method returns the expected date
        assertEquals(date, notification.getDate());
    }

    @Test
    void testGetNotificationType() {
        // Create a Notification object with a specific notification type
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.ASSIGN_TO_ME);

        // Verify that the getNotificationType() method returns the expected notification type
        assertEquals(NotificationType.ASSIGN_TO_ME, notification.getNotificationType());
    }

}
