package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.AuthenticationRequest;
import projectManagement.dto.BaseResponse;
import projectManagement.service.UserService;
import projectManagement.util.JwtUtils;

import static projectManagement.util.GitAuthUtil.*;

@Controller
@CrossOrigin
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static Logger logger = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Value("${github.clientId}")
    String gitClientId;
    @Value("${github.clientSecret}")
    String gitClientSecret;

    /**
     * This function handles user authentication.
     *
     * @param request An object containing the email and password for the user trying to authenticate
     * @return A ResponseEntity object with a BaseResponse body containing the result of the authentication and, if successful, a JWT token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("in authenticate(): ");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>("Bad credentials", request));
        }
        final UserDetails user = userService.loadUserByUsername(request.getEmail());
        return ResponseEntity.ok(new BaseResponse<>("Success", jwtUtils.generateToken(user)));
    }

    /**
     * make gt request with getEmailFromGit function
     * then check if user already signup with this email (regular register),
     * if false- check if user already signup with git.
     * if true- login, else- signup and login.
     *
     * @param code
     * @return user email
     */
    @GetMapping("/github")
    public ResponseEntity<BaseResponse> authWithGit(@RequestParam String code) {
        logger.info("in authWithGit(): ");
        try {
            String email = getEmailFromGit(code, gitClientId, gitClientSecret);
            final UserDetails user;

            if (userService.emailExists(email)) {
                user = userService.loadUserByUsername(email);
                if (!userService.isGithubAccount(user.getUsername())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new BaseResponse<>("Failed- user register already without github", user.getUsername()));
                }
            } else {
                user = userService.loadUserByUsername(userService.registerWithGit(email).getEmail());
            }
            return ResponseEntity.ok(new BaseResponse<>("Success", jwtUtils.generateToken(user)));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("git token is null", e.getMessage()));
        }
    }
}