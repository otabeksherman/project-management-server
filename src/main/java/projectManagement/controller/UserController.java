package projectManagement.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectManagement.service.AuthService;
import projectManagement.service.UserService;

public class UserController {
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());


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
