package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.exception.NotificationSendFailedException;
import projectManagement.exception.UserNotFoundException;
import projectManagement.repository.UserRepository;

import java.time.LocalDate;

import static projectManagement.util.MailUtil.sendMail;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static Logger logger = LogManager.getLogger(NotificationService.class);

    private final UserRepository userRepository;

    /**
     * the function get notification details, create notification, load it to user notification list.
     *
     * @param emailUser
     * @param emailAssigner
     * @param board
     * @param notificationtype
     * @return
     * @throws NotificationSendFailedException
     * @throws UserNotFoundException
     */
    public void addNotification(String emailUser, String emailAssigner, Board board, NotificationType notificationtype) throws NotificationSendFailedException, UserNotFoundException {
        logger.info("in addNotification(): ");
        if (userRepository.findUserByEmail(emailUser) == null && userRepository.findUserByEmail(emailAssigner) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", emailUser));
        } else {
            User user = userRepository.findUserByEmail(emailUser);
            User userAssigner = userRepository.findUserByEmail(emailAssigner);

            Notification notification = new Notification.Builder()
                    .user(user).assigner(userAssigner).board(board).date(LocalDate.now())
                    .notificationType(notificationtype).build();

            user.addNotifications(notification);
            userRepository.save(user);

            if (user.getNotificationTypeSettings().get(notification.getNotificationType())) {
                try {
                    sendNotification(user, emailAssigner, notification);
                } catch (NotificationSendFailedException e) {
                    throw new NotificationSendFailedException("Unable to send mail");
                }
            }
        }
    }

    /**
     * check if user want to get notification- if yes send the notification (by email or popup or both)
     *
     * @param user
     * @param emailAssigner
     * @param notification
     * @throws NotificationSendFailedException
     */
    private void sendNotification(User user, String emailAssigner, Notification notification) throws NotificationSendFailedException {
        if (user.getNotificationTypeSettings().get(notification.getNotificationType())) {
            if (user.getEmailNotify()) {
                String subject = "Project Management-email notification";
                String message = "notification from user: " + emailAssigner +
                        "notification: " + notification.getNotificationType().getText();//TODO: change the message
                try {
                    sendMail(user.getEmail(), subject, message);
                } catch (Exception e) {
                    throw new NotificationSendFailedException("Unable to send mail");
                }
            }
            if (user.getPopNotify()) {
                //TODO: popup notification
            }
        }
    }


}
