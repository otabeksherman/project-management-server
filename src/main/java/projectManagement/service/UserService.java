package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectManagement.dto.RegistrationDto;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.exception.NotificationSendFailedException;
import projectManagement.exception.UserNotFoundException;
import projectManagement.repository.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static projectManagement.util.MailUtil.sendMail;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    /**
     * Loads the user details for the given username.
     *
     * @param email The email of the user to load.
     * @return The UserDetails for the given email.
     * @throws UsernameNotFoundException If the user with the given email does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER"))); // TODO Set relevant roles
    }

    /**
     * Determines if the given email is associated with a GitHub account.
     *
     * @param email The email to check.
     * @return True if the email is associated with a GitHub account, false otherwise.
     */
    public boolean isGithubAccount(String email) {
        User user = userRepository.findUserByEmail(email);
        return user.isGithubAccount();
    }

    /**
     * Registers a new user with the given email and password.
     *
     * @param dto The RegistrationDto containing the email and password of the user to register.
     * @return The newly registered User.
     * @throws UserNotFoundException If the email already exists in the users table.
     */
    public User register(RegistrationDto dto) throws UserNotFoundException {
        if (userRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new UserNotFoundException(String.format("Email %s exists in users table", dto.getEmail()));
        } else {
            User user = new User(dto.getEmail(), dto.getPassword());
            return userRepository.save(user);
        }
    }

    /**
     * Registers a user with a Github account.
     *
     * @param email the email of the user to register
     * @return the saved user
     */
    public User registerWithGit(String email) {
        User user = new User(email);
        return userRepository.save(user);
    }

    /**
     * Determines if a given email exists in the user repository.
     *
     * @param email the email to check for
     * @return true if the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return (userRepository.findUserByEmail(email) != null);
    }

//    /**
//     * Updates the notification settings for a given user.
//     *
//     * @param email  the email of the user to update
//     * @param notify whether notifications should be enabled or disabled
//     * @param type   the type of notification to update (either "popup" or "email")
//     * @return the updated user
//     * @throws UserNotFoundException           if the user with the given email does not exist
//     * @throws NotificationSendFailedException if the given notification type is invalid
//     */
//    public User updateNotifyBy(String email, boolean notify, String type) throws UserNotFoundException, NotificationSendFailedException {
//        User user;
//        if (userRepository.findUserByEmail(email) == null) {
//            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
//        } else {
//            user = userRepository.findUserByEmail(email);
//            if (type.equals("popup")) {
//                user.setPopNotify(notify);
//            } else if (type.equals("email")) {
//                user.setEmailNotify(notify);
//
//                //send first email about update state to active
//                if (notify == true) {
//                    String subject = "email notification";
//                    String message = "Email notifications have been updated to active. From now on you will start receiving updates by email";
//                    try {
//                        sendMail(email, subject, message);
//                    } catch (Exception e) {
//                        throw new NotificationSendFailedException(String.format("failed to send email: ", email));
//                    }
//                }
//            } else {
//                throw new IllegalArgumentException(String.format("type %s is not exist", type));
//            }
//        }
//        return userRepository.save(user);
//    }

//    /**
//     * Registers a new user with the given email address and sets their notification preferences for
//     * popup notifications to the given value.
//     *
//     * @param email  the email address of the user to be registered
//     * @param notify whether the user wants to receive popup notifications
//     * @return the updated user object
//     * @throws UserNotFoundException if the given email does not exist in the users table
//     */
//    public User notifyByPopup(String email, boolean notify) throws UserNotFoundException {
//        User user;
//        if (userRepository.findUserByEmail(email) == null) {
//            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
//        } else {
//            user = userRepository.findUserByEmail(email);
//            user.setPopNotify(notify);
//        }
//        return userRepository.save(user);
//    }

    /**
     * Retrieves the set of notifications for the user with the given email address.
     *
     * @param email the email address of the user
     * @return the set of notifications for the user
     * @throws UserNotFoundException if the given email does not exist in the users table
     */
    public Set<Notification> getUserNotification(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
        } else {
            User user = userRepository.findUserByEmail(email);
            return user.getNotifications();
        }
    }

    /**
     * Retrieves the set of notifications for the user with the given email address.
     *
     * @param email the email address of the user
     * @return the set of notifications for the user
     * @throws UserNotFoundException if the given email does not exist in the users table
     */
    public Map<NotificationType, Boolean> getUserNotificationTypeNotificationEmail(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
        } else {
            User user = userRepository.findUserByEmail(email);
            return user.getNotificationTypeSettingsEmail();
        }
    }

    /**
     * Retrieves the set of notifications for the user with the given email address.
     *
     * @param email the email address of the user
     * @return the set of notifications for the user
     * @throws UserNotFoundException if the given email does not exist in the users table
     */
    public Map<NotificationType, Boolean> getUserNotificationTypeNotificationPopup(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
        } else {
            User user = userRepository.findUserByEmail(email);
            return user.getNotificationTypeSettingsPopup();
        }
    }

//    /**
//     * Retrieves the notification preferences for the user with the given email address for each type
//     * of notification.
//     *
//     * @param email the email address of the user
//     * @return a map of notification types to boolean values indicating whether the user wants to receive
//     * notifications of that type
//     * @throws UserNotFoundException if the given email does not exist in the users table
//     */
//    public Map<String, Boolean> getUserNotificationBySettings(String email) throws UserNotFoundException {
//        if (userRepository.findUserByEmail(email) == null) {
//            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
//        } else {
//            User user = userRepository.findUserByEmail(email);
//            Map<String, Boolean> notificationBySettings = new HashMap<>();
//            notificationBySettings.put("email", user.getEmailNotify());
//            notificationBySettings.put("popup", user.getPopNotify());
//            return notificationBySettings;
//        }
//    }

    /**
     * Updates the notification settings for the specified notification type for the user with the given email.
     *
     * @param email               The email of the user to update the notification settings for.
     * @param notificationTypeStr The notification type to update the settings for.
     * @param update              The new value for the notification setting.
     * @return The updated user.
     * @throws UserNotFoundException If a user with the given email does not exist in the users table.
     */
    public User updateEmailNotificationTypeSettings(String email, String notificationTypeStr, Boolean update) throws UserNotFoundException, IllegalArgumentException {
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
        } else {
            NotificationType notificationType;
            try {
                notificationType = NotificationType.valueOf(notificationTypeStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("notification type %s is not exists", notificationTypeStr));
            }
            user = userRepository.findUserByEmail(email);
            user.updateNotificationTypeSettingByEmail(notificationType, update);
        }
        return userRepository.save(user);
    }

    /**
     * Updates the notification settings for the specified notification type for the user with the given email.
     *
     * @param email               The email of the user to update the notification settings for.
     * @param notificationTypeStr The notification type to update the settings for.
     * @param update              The new value for the notification setting.
     * @return The updated user.
     * @throws UserNotFoundException If a user with the given email does not exist in the users table.
     */
    public User updatePopupNotificationTypeSettings(String email, String notificationTypeStr, Boolean update) throws UserNotFoundException, IllegalArgumentException {
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new UserNotFoundException(String.format("Email %s is not exists in users table", email));
        } else {
            NotificationType notificationType;
            try {
                notificationType = NotificationType.valueOf(notificationTypeStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("notification type %s is not exists", notificationTypeStr));
            }
            user = userRepository.findUserByEmail(email);
            user.updateNotificationTypeSettingByPopup(notificationType, update);
        }
        return userRepository.save(user);
    }

}



