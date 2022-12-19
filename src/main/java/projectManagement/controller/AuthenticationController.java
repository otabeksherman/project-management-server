package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import projectManagement.dto.BaseResponse;
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
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>("Bad credentials", request));
        }
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        return ResponseEntity.ok(new BaseResponse<>("Success", jwtUtils.generateToken(user)));
    }
}

