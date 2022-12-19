package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("api/v1/greetings")
@RequiredArgsConstructor
public class GreetingsController {
    @GetMapping
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("Hello");
    }
}
