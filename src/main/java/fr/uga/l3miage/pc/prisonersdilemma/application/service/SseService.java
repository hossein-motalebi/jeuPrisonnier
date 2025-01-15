package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class SseService {

    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(int idPartie) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(idPartie, emitter);

        emitter.onCompletion(() -> emitters.remove(idPartie));
        emitter.onTimeout(() -> emitters.remove(idPartie));

        return emitter;
    }

    public void sendEvent(int idPartie, Object event) {
        SseEmitter emitter = emitters.get(idPartie);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("update").data(event));
            } catch (IOException e) {
                emitters.remove(idPartie);
            }
        }
    }
}