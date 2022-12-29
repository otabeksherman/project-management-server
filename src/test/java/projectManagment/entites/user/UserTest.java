package projectManagment.entites.user;
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

        assertNotNull(user.getBoards());
        assertNotNull(user.getNotifications());
    }

}
