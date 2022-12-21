package projectManagement.controller;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
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
import projectManagement.util.JwtUtils;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;
    private static Logger logger = LogManager.getLogger(UserController.class);
    JwtUtils jwtUtils;

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
    public ResponseEntity<BaseResponse> notifyByEmail(@RequestParam String token, @RequestParam boolean notify) {
        logger.info("in notifyByEmail(): ");
        String email= jwtUtils.extractUsername(token);
        try {
            return ResponseEntity.ok(new BaseResponse<>("Email notify updated", userService.notifyByEmail(email, notify).getEmail()));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Unable to send mail", null));
        }
    }
    @GetMapping("/userNotification")
    public ResponseEntity<BaseResponse> getUserNotification(@RequestParam String token){
        String email= jwtUtils.extractUsername(token);
        try {
            return ResponseEntity.ok(new BaseResponse<>("User notification list", userService.getUserNotification(email)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

}
