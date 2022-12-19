package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public ResponseEntity<String> register(@RequestBody RegistrationDto dto) {
        try {
            return new ResponseEntity<>(userService.register(dto).getEmail(), HttpStatus.CREATED);
        } catch (SQLDataException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already exists", e);
        }
    }


    @GetMapping("/registrationToGit")
    public ResponseEntity<String> registerToGit(@RequestParam String code) {
        logger.info("in authenticateToGit(): ");
        return new ResponseEntity<>(userService.registerWithGit(code), HttpStatus.CREATED);
    }


}
