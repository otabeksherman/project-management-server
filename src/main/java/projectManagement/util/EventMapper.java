package projectManagement.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import projectManagement.dto.EventDto;

@Component
@AllArgsConstructor
public class EventMapper {
    public SseEmitter.SseEventBuilder toSseEventBuilder(Long boardId, EventDto event) {
        return SseEmitter.event()
                .id(boardId.toString())
                .name(event.getType())
                .data(event.getBody());
    }
}
