package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projectManagement.dto.BaseResponse;
import projectManagement.dto.RegistrationDto;
import projectManagement.entities.user.User;
import projectManagement.service.NotificationService;
import projectManagement.service.UserService;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;
    private static Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping("/registration")
    public ResponseEntity<BaseResponse> register(@RequestBody RegistrationDto dto) {
        logger.info("in register(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Account created successfully",
                            userService.register(dto).getEmail()));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>("Email already exists", dto.getEmail()));
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<BaseResponse> notifyByEmail(@RequestParam String email, @RequestParam boolean notify) throws Exception {
        logger.info("in notifyByEmail(): ");
        try {
            return ResponseEntity.ok(new BaseResponse<>("Email notify updated", userService.notifyByEmail(email, notify).getEmail()));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

}
