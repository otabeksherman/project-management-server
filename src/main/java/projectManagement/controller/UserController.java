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
import projectManagement.entities.notifictaion.Notification;
import projectManagement.entities.notifictaion.NotificationType;
import projectManagement.entities.user.User;
import projectManagement.exception.NotificationSendFailedException;
import projectManagement.exception.UserNotFoundException;
import projectManagement.service.UserService;

import java.util.Map;
import java.util.Set;

@Controller
@CrossOrigin
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static Logger logger = LogManager.getLogger(UserController.class);

    /**
     * registration of a new user. It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @return user email
     */
    @PostMapping("/registration")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody RegistrationDto dto) {
        logger.info("in register(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Account created successfully",
                            userService.register(dto).getEmail()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>("Email already exists", dto.getEmail()));
        }
    }

//    /**
//     * get boolean notify, change to active/ unactive notifyBy Email/Popup user's setting.
//     * if notify is true- also send first notify email.
//     *
//     * @param notify
//     * @return BaseResponse with user's email.
//     */
//    @PatchMapping("/updateNotifyBy")
//    public ResponseEntity<BaseResponse<String>> updateNotifyBy(@RequestParam boolean notify, @RequestParam String type, @RequestAttribute String userEmail) {
//        logger.info("in notifyByEmail(): ");
//        try {
//            return ResponseEntity.ok(new BaseResponse<>("notify by " + type + " updated", userService.updateNotifyBy(userEmail, notify, type).getEmail()));
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", userEmail));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("type %s is not exist", type));
//        } catch (NotificationSendFailedException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Unable to send mail", userEmail));
//        }
//    }

    /**
     * ger user email and return it's notification list
     *
     * @return user notification list
     */
    @GetMapping("/userNotification")
    public ResponseEntity<BaseResponse<Set<Notification>>> getUserNotification(@RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("User notification list", userService.getUserNotification(userEmail)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

    /**
     * get user and notification type setting, and update user setting.
     *
     * @param userEmail
     * @param notificationType
     * @param update
     * @return User
     */
    @PatchMapping("/updateEmailNotificationType")
    public ResponseEntity<BaseResponse<User>> updateEmailNotificationTypeSettings(@RequestAttribute String userEmail, @RequestParam String notificationType, @RequestParam Boolean update) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("updateNotificationTypeSettings", userService.updateEmailNotificationTypeSettings(userEmail, notificationType, update)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("notification type %s is not exists", null));
        }
    }

    /**
     * get user and notification type setting, and update user setting.
     *
     * @param userEmail
     * @param notificationType
     * @param update
     * @return User
     */
    @PatchMapping("/updateNotificationType")
    public ResponseEntity<BaseResponse<User>> updatePopupNotificationTypeSettings(@RequestAttribute String userEmail, @RequestParam String notificationType, @RequestParam Boolean update) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("updateNotificationTypeSettings", userService.updatePopupNotificationTypeSettings(userEmail, notificationType, update)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("notification type %s is not exists", null));
        }
    }

    /**
     * get user notification type setting (map of type and boolean)
     *
     * @param userEmail
     * @return map<type, boolean>
     */
    @GetMapping("/getUserNotificationTypeEmail")
    public ResponseEntity<BaseResponse<Map<NotificationType, Boolean>>> getUserNotificationTypeNotificationEmail(@RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("User notification type:", userService.getUserNotificationTypeNotificationEmail(userEmail)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

    /**
     * get user notification type setting (map of type and boolean)
     *
     * @param userEmail
     * @return map<type, boolean>
     */
    @GetMapping("/getUserNotificationTypePopup")
    public ResponseEntity<BaseResponse<Map<NotificationType, Boolean>>> getUserNotificationTypeNotificationPopup(@RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok(new BaseResponse<>("User notification type:", userService.getUserNotificationTypeNotificationPopup(userEmail)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
        }
    }

//    /**
//     * return notification by setting (is email and popup are active)
//     *
//     * @param userEmail
//     * @return map <string (email/popup), boolean>
//     */
//    @GetMapping("/getUserNotificationBySettings")
//    public ResponseEntity<BaseResponse<Map<String, Boolean>>> getUserNotificationBySettings(@RequestAttribute String userEmail) {
//        try {
//            return ResponseEntity.ok(new BaseResponse<>("User notification by settings:", userService.getUserNotificationBySettings(userEmail)));
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Email %s is not exists in users table", null));
//        }
//    }
}
