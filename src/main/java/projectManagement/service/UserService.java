package projectManagement.service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import projectManagement.dto.RegistrationDto;
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.repository.UserRepository;
import projectManagement.util.JwtUtils;

import java.sql.SQLDataException;
import java.util.Collections;
import java.util.Set;

import static projectManagement.util.MailUtil.sendMail;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER"))); // TODO Set relevant roles
    }

    public boolean isGithubAccount(String email) {
        User user = userRepository.findUserByEmail(email);
        return user.isGithubAccount();
    }

    public User register(RegistrationDto dto) throws SQLDataException {
        if (userRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new SQLDataException(String.format("Email %s exists in users table", dto.getEmail()));
        } else {
            User user = new User(dto.getEmail(), dto.getPassword());
            return userRepository.save(user);
        }
    }

    public User registerWithGit(String email) {
        User user = new User(email);
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return (userRepository.findUserByEmail(email) != null);
    }

    public User updateNotifyBy(String email, boolean notify, String type) throws Exception ,IllegalArgumentException{
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", email));
        } else {
            user = userRepository.findUserByEmail(email);
            if(type.equals("popup")){
                user.setPopNotify(notify);
            } else if( type.equals("email")) {
                user.setEmailNotify(notify);

                //send first email about update state to active
                if (notify == true) {
                    String subject = "email notification";
                    String message = "Email notifications have been updated to active. From now on you will start receiving updates by email";
                    try {
                        sendMail(email, subject, message);
                    } catch (IllegalArgumentException e) {
                        throw new Exception(String.format("failed to send email: ", email));
                    }
                }
            }else{
                throw new IllegalArgumentException(String.format("type %s is not exist", type));
            }
        }
        return userRepository.save(user);
    }

    public User notifyByPopup(String email, boolean notify) throws SQLDataException {
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", email));
        } else {
            user = userRepository.findUserByEmail(email);
            user.setPopNotify(notify);
        }
        return userRepository.save(user);
    }

    public Set<Notification> getUserNotification(String email) throws SQLDataException {
        if (userRepository.findUserByEmail(email) == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", email));
        } else {
            User user = userRepository.findUserByEmail(email);
            return user.getNotifications();
        }
    }

    public User updateNotificationTypeSettings(String email, String notificationTypeStr, Boolean update) throws SQLDataException, IllegalArgumentException {
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", email));
        } else {
            NotificationType notificationType;
            try {
                notificationType = NotificationType.valueOf(notificationTypeStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("notification type %s is not exists", notificationTypeStr));
            }
            user = userRepository.findUserByEmail(email);
            user.updateNotificationTypeSetting(notificationType, update);
        }
        return userRepository.save(user);
    }

}



