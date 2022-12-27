package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import projectManagement.repository.EmitterRepository;

@Service
@RequiredArgsConstructor
public class EmitterService {

    private final long eventsTimeout = 18000;
    private final EmitterRepository repository;

    public SseEmitter createEmitter(String memberId, Long boardId) {
        SseEmitter emitter = new SseEmitter(-1L);
        emitter.onCompletion(() -> repository.remove(memberId));
        emitter.onTimeout(emitter::complete);
        emitter.onError(e -> {
            repository.remove(memberId);
        });
        repository.addOrReplaceEmitter(memberId, boardId, emitter);
        return emitter;
    }
}
