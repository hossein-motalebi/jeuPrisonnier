package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private final ConcurrentHashMap<Integer, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    // Cr�e et ajoute un nouvel �metteur pour un idPartie donn�
    public SseEmitter createEmitter(int idPartie) {
        SseEmitter emitter = new SseEmitter(0L); // Pas de d�lai d'expiration

        // Ajoute l'�metteur � la liste associ�e � idPartie
        emitters.computeIfAbsent(idPartie, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(idPartie, emitter));
        emitter.onTimeout(() -> removeEmitter(idPartie, emitter));
        return emitter;
    }

    // Envoie un �v�nement � tous les �metteurs associ�s � un idPartie
    public void sendEvent(int idPartie, Object event) {
        List<SseEmitter> emittersList = emitters.get(idPartie);
        if (emittersList != null) {
            emittersList.forEach(emitter -> {
                try {
                    emitter.send(SseEmitter.event().name("update").data(event));
                } catch (IOException e) {
                    removeEmitter(idPartie, emitter);
                }
            });
        }
    }

    // Supprime un �metteur de la liste
    private void removeEmitter(int idPartie, SseEmitter emitter) {
        List<SseEmitter> emittersList = emitters.get(idPartie);
        if (emittersList != null) {
            emittersList.remove(emitter);
            if (emittersList.isEmpty()) {
                emitters.remove(idPartie);
            }
        }
    }
}