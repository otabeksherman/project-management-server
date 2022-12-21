package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Component;
import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.repository.UserRepository;
import java.sql.SQLDataException;
import java.time.LocalDate;

import static projectManagement.util.MailUtil.sendMail;

@Component
@RequiredArgsConstructor
public class NotificationService {
    private static Logger logger = LogManager.getLogger(NotificationService.class);

    private final UserRepository userRepository;

    public void addNotification(String emailUser,String emailAssigner,Board board, NotificationType notificationtype) throws Exception {
        logger.info("in addNotification(): ");
        if (userRepository.findUserByEmail(emailUser) == null && userRepository.findUserByEmail(emailAssigner)== null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", emailUser));
        } else {
            User user = userRepository.findUserByEmail(emailUser);
            User userAssigner = userRepository.findUserByEmail(emailAssigner);

            Notification notification=new Notification.Builder()
                    .user(user).assigner(userAssigner).board(board).date(LocalDate.now())
                    .notificationType(notificationtype).build();

            user.addNotifications(notification);
            if(notification.getNotificationType().isTypeActive()) {

                if (user.getEmailNotify()) {
                    String subject = "Project Management-email notification";
                    String message = "notification from user: " + emailAssigner +
                            "notification: " + notification.getNotificationType().getText();//TODO: change the message
                    try {
                        sendMail(user.getEmail(), subject, message);
                    } catch (Exception e) {
                        throw new Exception("Unable to send mail");
                    }
                }
                if (user.getPopNotify()) {
                    //TODO: implement return to client with docket/sse
                }
            }
        }
    }
}
