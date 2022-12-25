package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.BaseResponse;
import projectManagement.dto.RegistrationDto;
import projectManagement.service.UserService;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
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

    /**
     * get boolean notify, change to active/ unactive notifyByPopup user's setting.
     *
     * @param notify
     * @param userEmail
     * @return
     */
    @PostMapping("/notifyByPopup")
    public ResponseEntity<BaseResponse> updateNotifyByPopup(@RequestParam boolean notify, @RequestAttribute String userEmail) {
        logger.info("in updateNotifyByPopup(): ");
        try {
            return ResponseEntity.ok(new BaseResponse<>("Email notify updated", userService.notifyByPopup(userEmail, notify).getEmail()));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

    /**
     * get boolean notify, change to active/ unactive notifyByEmail user's setting.
     * if notify is true- also send first notify email.
     *
     * @param notify
     * @return BaseResponse with user's email.
     */
    @PostMapping("/notifyByEmail")
    public ResponseEntity<BaseResponse> updateNotifyByEmail(@RequestParam boolean notify, @RequestAttribute String userEmail) {
        logger.info("in notifyByEmail(): ");
        try {
            return ResponseEntity.ok(new BaseResponse<>("Email notify updated", userService.notifyByEmail(userEmail, notify).getEmail()));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Unable to send mail", null));
        }
    }

    /**
     * @return user notification list
     */
    @GetMapping("/userNotification")
    public ResponseEntity<BaseResponse> getUserNotification(@RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("User notification list", userService.getUserNotification(userEmail)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

    /**
     * get user and notification type setting, and update user setting.
     * @param userEmail
     * @param notificationType
     * @param update
     * @return
     */
    @PostMapping("/updateNotificationType")
    public ResponseEntity<BaseResponse> updateNotificationTypeSettings(@RequestAttribute String userEmail,@RequestParam String notificationType,@RequestParam Boolean update) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("updateNotificationTypeSettings", userService.updateNotificationTypeSettings(userEmail, notificationType, update)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("notification type %s is not exists", notificationType));
        }
    }
}
