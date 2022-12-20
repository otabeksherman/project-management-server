package projectManagement.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import projectManagement.dto.RegistrationDto;
import projectManagement.entities.user.User;
import projectManagement.repository.UserRepository;

import java.sql.SQLDataException;
import java.util.Collections;

import static projectManagement.util.NotificationUtil.sendMail;

@Component
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
        if (userRepository.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }

    public User notifyByEmail(String email, boolean notify) throws Exception {
        User user;
        if (userRepository.findUserByEmail(email) == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", email));
        } else {
            user = userRepository.findUserByEmail(email);
            user.setEmailNotify(notify);
            if (notify == true) {
                String subject = "email notification";
                String message = "email notifications have been updated to active. From now on you will start receiving updates by email";
                try {
                    sendMail(email, subject, message);
                } catch (GoogleJsonResponseException e) {
                    throw new Exception("Unable to send mail");
                }
            }
        }
        return userRepository.save(user);
    }
}



