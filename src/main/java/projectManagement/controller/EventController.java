package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import projectManagement.service.EmitterService;

@Controller
@CrossOrigin
@RequestMapping("api/v1/subscribe")
@RequiredArgsConstructor
public class EventController {

    private final EmitterService emitterService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToEvents(@RequestParam Long boardId, @RequestAttribute String userEmail) {
        return emitterService.createEmitter(userEmail, boardId);
    }
}
