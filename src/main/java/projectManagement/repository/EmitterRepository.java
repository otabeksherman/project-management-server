package projectManagement.repository;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import projectManagement.entities.Emmiter.UserEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private static Logger logger = LogManager.getLogger(EmitterRepository.class);

    private Map<String, SseEmitter> userEmitterMap = new ConcurrentHashMap<>();
    private Map<String, UserEmitter> boardEmitterMap = new ConcurrentHashMap<>();

    public void addOrReplaceEmitter(String memberId, Long boardId, SseEmitter emitter) {
        UserEmitter userEmitter = new UserEmitter(boardId, emitter);
        boardEmitterMap.put(memberId, userEmitter);
        userEmitterMap.put(memberId, emitter);
    }

    public void remove(String memberId) {
        if (boardEmitterMap != null && boardEmitterMap.containsKey(memberId)) {
            boardEmitterMap.remove(memberId);
        } else {
            logger.debug("No emitter to remove for member: {}", memberId);
        }
    }

    public Optional<UserEmitter> get(String memberId) {
        return Optional.ofNullable(boardEmitterMap.get(memberId));
    }

    public Map<String, UserEmitter> getAllEmitters() {
        return boardEmitterMap;
    }
}
