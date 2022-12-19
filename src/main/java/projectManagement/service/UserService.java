package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import projectManagement.dto.RegistrationDto;
import projectManagement.repository.UserRepository;
import projectManagement.entities.user.User;
import projectManagement.util.*;

import java.sql.SQLDataException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Value("${github.clientId}")
    String git_client_id;
    @Value("${github.clientSecret}")
    String git_client_secret;

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

    public User registerWithGit(String code) throws SQLDataException {
        logger.info("in registerToGit():");
        String token = GitAuthUtil.getGitToken(code, git_client_id, git_client_secret);
        logger.debug("git token:"+ token);
        String email=GitAuthUtil.getGitEmailFromToken(token);

        if (userRepository.findUserByEmail(email) != null) {
            throw new SQLDataException(String.format("Email %s exists in users table", email));
        } else {
            User user = new User(email);
            return userRepository.save(user);
        }
    }



}


