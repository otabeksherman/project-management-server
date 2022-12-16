package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectManagement.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void updateEmail(String email, String newEmail) {
        //TODO
    }

     public void updateName(String email, String name) {
        //TODO
    }
    public void updatePassword(String email, String password) {
        //TODO
    }

    public void deleteUser(Long id) {
        //TODO
    }

}
