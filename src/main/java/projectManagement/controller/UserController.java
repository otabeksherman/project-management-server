package projectManagement.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectManagement.controller.response.BaseResponse;
import projectManagement.entities.DTO.UserDTO;
import projectManagement.entities.user.User;
import projectManagement.service.AuthService;
import projectManagement.service.UserService;
import projectManagement.utils.InputValidation;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<UserDTO>> getUserByEmail(@RequestParam String email) {
        logger.info("in getUserByEmail");

        Optional<UserDTO> user = userService.getByEmail(email);
        return user.map(value -> ResponseEntity.ok(BaseResponse.success(value)))
                .orElseGet(() -> ResponseEntity.badRequest().body(BaseResponse.failure("User not found!")));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "delete")
    public ResponseEntity<BaseResponse<String>> deleteUser(@RequestParam Long userId, @RequestHeader String token) {
        logger.debug("in deleteUser");

        if (!authService.isAuthenticated(userId, token)) {
            return ResponseEntity.badRequest().body(BaseResponse.failure("User is not logged-in!"));
        }

        if (userService.deleteUser(userId)) {
            return ResponseEntity.ok(BaseResponse.noContent(true, "User #" + userId + " was successfully deleted"));
        }

        return ResponseEntity.badRequest().body(BaseResponse.failure("Failed to delete user #" + userId));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update/{id}", params = "newEmail")
    public ResponseEntity<BaseResponse<UserDTO>> updateEmail(@PathVariable("id") Long userId,
                                                             @RequestHeader String token, @RequestParam String newEmail) {
        logger.debug("in updateEmail");

        if (!InputValidation.isValidEmail(newEmail)) {
            return ResponseEntity.badRequest().body(BaseResponse.failure("Invalid email!"));
        }

        if (!authService.isAuthenticated(userId, token)) {
            return ResponseEntity.badRequest().body(BaseResponse.failure("User is not logged-in!"));
        }

        Optional<UserDTO> updatedUser = userService.updateEmail(userId, newEmail);
        return updatedUser.map(user -> ResponseEntity.ok(BaseResponse.success(user)))
                .orElseGet(() -> ResponseEntity.badRequest().body(BaseResponse.failure("User not found")));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update/{id}", params = "password")
    public ResponseEntity<BaseResponse<UserDTO>> updatePassword(@PathVariable("id") Long userId,
                                                                @RequestHeader String token, @RequestParam String password) {
        logger.debug("in updatePassword");

        if (!InputValidation.isValidPassword(password)) {
            return ResponseEntity.badRequest().body(BaseResponse.failure("Invalid password!"));
        }

        if (!authService.isAuthenticated(userId, token)) {
            return ResponseEntity.badRequest().body(BaseResponse.failure("User is not logged-in!"));
        }

        Optional<UserDTO> updatedUser = userService.updatePassword(userId, password);
        return updatedUser.map(user -> ResponseEntity.ok(BaseResponse.success(user)))
                .orElseGet(() -> ResponseEntity.badRequest().body(BaseResponse.failure("User not found")));
    }
}
