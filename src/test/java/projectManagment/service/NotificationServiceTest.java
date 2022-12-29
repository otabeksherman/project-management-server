package projectManagment.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.exception.NotificationSendFailedException;
import projectManagement.exception.UserNotFoundException;
import projectManagement.repository.UserRepository;
import projectManagement.service.NotificationService;

@ExtendWith(MockitoExtension.class)

public class NotificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

//    @Test
//    public void testAddNotification() throws NotificationSendFailedException, UserNotFoundException {
//
//        Board board = new Board();
//        User user = new User("test@example.com", "Aa123456!");
//        User userAssigner = new User("assigner@example.com", "Aa123456!");
//
//        when(userRepository.findUserByEmail(emailUser)).thenReturn(user);
//        when(userRepository.findUserByEmail(emailAssigner)).thenReturn(userAssigner);
//
//        notificationService.addNotification(emailUser, emailAssigner, board, NotificationType.ASSIGN_TO_ME);
//
//        user.setNotificationTypeSettings();ficationTypeSettings()
//        verify(userRepository).findUserByEmail(emailUser);
//        verify(userRepository).findUserByEmail(emailAssigner);
//        verify(user).addNotifications(any(Notification.class));
//        verify(userRepository).save(user);
//    }


}
