package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import projectManagement.dto.RegistrationDto;
import projectManagement.repository.UserRepository;
import projectManagement.entities.user.User;

import java.sql.SQLDataException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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
}
