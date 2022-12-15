package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.entities.DTO.UserDTO;
import projectManagement.entities.user.User;
import projectManagement.repository.PermissionRepository;
import projectManagement.repository.UserRepository;

import java.util.Optional;

import static org.aspectj.runtime.internal.Conversions.intValue;
import static projectManagement.utils.Utils.hashPassword;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public Optional<UserDTO> getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new UserDTO(user.get()));
    }

    public Long getUserIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            return null;
        }

        return user.get().getId();
    }

    public boolean deleteUser(Long id) {

        int lines = userRepository.deleteById(intValue(id));
        logger.debug("lines deleted: " + lines);

        if (lines == 1) {
            logger.debug("User #" + id + " deleted: ");
            return true;
        }
        return false;
    }

    public Optional<UserDTO> updateEmail(Long id, String email) {
        int lines = userRepository.updateUserEmailById(intValue(id), email);
        logger.debug("lines updated: " + lines);

        return getUpdatedUser(id, lines);
    }

    public Optional<UserDTO> updatePassword(Long id, String password) {
        int lines = userRepository.updateUserPasswordById(intValue(id),hashPassword(password));
        logger.debug("lines updated: " + lines);

        return getUpdatedUser(id, lines);
    }

    public Optional<UserDTO> updateEnabled(Long id, Boolean enabled) {
        int lines = userRepository.updateUserEnabledById(intValue(id), enabled);
        logger.debug("lines updated: " + lines);

        return getUpdatedUser(id, lines);
    }

    private Optional<UserDTO> getUpdatedUser(Long id, int lines) {
        if (lines == 1) {
            Optional<User> user = userRepository.findById(id);
            logger.debug("User #" + id + " updated: " + user.get());
            UserDTO userDTO = new UserDTO(user.get());
            return Optional.of(userDTO);
        }
        return Optional.empty();
    }
}
