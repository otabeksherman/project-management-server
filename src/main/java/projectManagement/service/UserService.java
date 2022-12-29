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
import projectManagement.exception.UserNotFoundException;
import projectManagement.repository.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

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



