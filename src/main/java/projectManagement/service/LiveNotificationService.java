package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.EventDto;
import projectManagement.repository.EmitterRepository;
import projectManagement.util.EventMapper;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class LiveNotificationService {

    private static Logger logger = LogManager.getLogger(LiveNotificationService.class);
    private final EventMapper eventMapper;

    private final EmitterRepository emitterRepository;

    public void sendNotification(String memberId, Long boardId, EventDto event) {
        if (event == null) {
            return;
        }
        doSendNotification(memberId, boardId, event);
    }

    private void doSendNotification(String memberId, Long boardId, EventDto event) {
        emitterRepository.getAllEmitters().forEach((s, emitter) -> {
            try {
                if (emitter.getBoardId() == boardId) {
                    emitter.getEmitter().send(eventMapper.toSseEventBuilder(boardId, event));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
