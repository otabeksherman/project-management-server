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

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLDataException;
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

            /**
             * check if user notification type setting is active (on email)
             * if true- try to send notification
             */
            if (user.getNotificationTypeSettingsEmail().get(notification.getNotificationType())) {
                try {
                    sendEmailNotification(user, emailAssigner, notification);
                } catch (NotificationSendFailedException e) {
                    throw new NotificationSendFailedException("Unable to send mail");
                }
            if (user.getNotificationTypeSettings().get(notification.getNotificationType())) {

                sendNotification(user, emailAssigner, notification);

            }

            /**
             * check if user notification type setting is active (on popup)
             * if true- try to send notification
             */
            if (user.getNotificationTypeSettingsPopup().get(notification.getNotificationType())) {
                sendPopupNotification(user, emailAssigner, notification);
            }
        }
    }

    /**
     * send the notification by email
     *
     * @param user
     * @param emailAssigner
     * @param notification
     * @throws NotificationSendFailedException
     */
    private void sendEmailNotification(User user, String emailAssigner, Notification notification) throws NotificationSendFailedException {
        String subject = "Project Management-email notification";
        String message = "notification from user: " + emailAssigner +
                "notification: " + notification.getNotificationType().getText();
        try {
            sendMail(user.getEmail(), subject, message);
        } catch (Exception e) {
            throw new NotificationSendFailedException("Unable to send mail");
        }
    }


    /**
     * send the notification by popup
     *
     * @param user
     * @param emailAssigner
     * @param notification
     */
    private void sendPopupNotification(User user, String emailAssigner, Notification notification) {

        String subject = "Project Management-email notification";
        String message = "notification from user: " + emailAssigner +
                "notification: " + notification.getNotificationType().getText();
        //TODO: popup notification
    private void sendNotification(User user, String emailAssigner, Notification notification) throws Exception {
        if (user.getNotificationTypeSettings().get(notification.getNotificationType())) {
            if (user.getEmailNotify()) {
                String subject = "Project Management-email notification";
                String message = "notification from user: " + emailAssigner +
                        "notification: " + notification.getNotificationType().getText();//TODO: change the message
                new Thread(() -> {
                    try {
                        sendMail(user.getEmail(), subject, message);
                    } catch (MessagingException | IOException | GeneralSecurityException e) {
                        logger.error("failed to send email");
                    }
                }).start();

            }
            if (user.getPopNotify()) {
                //TODO: popup notification
            }
        }
    }

    }

}
