package projectManagement.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectManagement.controller.request.UserRequest;
import projectManagement.service.AuthService;
import projectManagement.service.UserService;


public class AuthController {
    private AuthService authService;
    private UserService userService;
    private static final Logger logger = LogManager.getLogger(AuthController.class.getName());


    public void register(UserRequest userRequest) {
        logger.info("in register()");
        //TODO
    }

    /*
    public String login(UserRequest userRequest) {
        logger.info("in login()");
        //TODO
    }
     */
}
