package projectManagement.entities.Emmiter;

import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
public class UserEmitter {
    Long boardId;
    SseEmitter emitter;
    public UserEmitter(Long boardId, SseEmitter emitter) {
        this.boardId = boardId;
        this.emitter = emitter;
    }
}
