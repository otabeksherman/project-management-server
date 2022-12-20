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
import org.springframework.web.client.RestClientException;
import projectManagement.dto.BaseResponse;
import projectManagement.util.JwtUtils;
import projectManagement.dto.AuthenticationRequest;
import projectManagement.service.UserService;

import javax.swing.text.html.HTML;

import java.net.URI;

import static projectManagement.util.GitAuthUtil.getAuthLinkFromGit;
import static projectManagement.util.GitAuthUtil.getEmailFromGit;

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

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request) {
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
    @GetMapping("/authorizeGithub")
    public ResponseEntity<BaseResponse> authorizeGithub() {
        URI link=getAuthLinkFromGit(gitClientId);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location",
                String.valueOf(link)).body(new BaseResponse<>("Success", link));
    }

    @GetMapping("/github")
    public ResponseEntity<BaseResponse> authWithGit(@RequestParam String code) {
        logger.info("in authWithGit(): ");
        try {
            String email = getEmailFromGit(code, gitClientId, gitClientSecret);
            final UserDetails user;

            if (userService.emailExists(email)) {
                user = userService.loadUserByUsername(email);
            } else {
                user = userService.loadUserByUsername(userService.registerWithGit(email).getEmail());
            }
            return ResponseEntity.ok(new BaseResponse<>("Success", jwtUtils.generateToken(user)));
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>("Failed",e.getMessage()));
        }
    }

}
