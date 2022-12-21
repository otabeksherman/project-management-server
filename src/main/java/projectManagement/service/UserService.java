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
}



