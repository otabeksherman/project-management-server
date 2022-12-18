package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import projectManagement.util.JwtUtils;
import projectManagement.dto.AuthenticationRequest;
import projectManagement.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if (user != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Error has occurred");
    }
}

