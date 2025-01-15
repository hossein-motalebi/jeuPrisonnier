package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.DecisionUseCases;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionInDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DecisionServiceImpl implements DecisionUseCases {

    Map<Integer, DecisionDTO> historiqueDecision; // contient le dernier decision de chaque partie
    PartieServiceImpl partieServiceImpl;
    SseService sseService;

    public DecisionServiceImpl(PartieServiceImpl partieServiceImpl, SseService sseService) {
        historiqueDecision = new HashMap<>();
        this.partieServiceImpl = partieServiceImpl;
        this.sseService = sseService;

    }

    public void verifieDecisionEnAbandonne(int idPartie, int idJoueurAbandonne) {
        if (historiqueDecision.containsKey(idPartie)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            traiterDecisionDejaPresente(idPartie, idJoueurAbandonne, null);
        }
    }

    private void traiterDecisionDejaPresente(int idPartie, int idJoueur, Decision decision) {
        DecisionDTO decisionDTO = historiqueDecision.get(idPartie);
        if (idJoueur == 1) {
            verifierDecisionNonPrise(decisionDTO.decisionJoueur1(), "La decision du joueur 1 est d�j� prise");
            decisionDTO = new DecisionDTO(decision, decisionDTO.decisionJoueur2());
        } else {
            verifierDecisionNonPrise(decisionDTO.decisionJoueur2(), "La decision du joueur 2 est d�j� prise");
            decisionDTO = new DecisionDTO(decisionDTO.decisionJoueur1(), decision);
        }
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO, idPartie);
        sseService.sendEvent(idPartie, outPartieDTO);
        historiqueDecision.remove(idPartie);
    }

    private void verifierDecisionNonPrise(Decision decisionExistante, String messageErreur) {
        if (decisionExistante != null) {
            throw new IllegalArgumentException(messageErreur);
        }
    }

    private void traiterNouvelleDecision(int idPartie, int idJoueur, Decision decision) {
        Partie partie = partieServiceImpl.getParties().get(idPartie);
        if (idJoueur == 1) {
            if (partie.getJoueur2() instanceof JoueurBot) {
                sseService.sendEvent(idPartie, partieServiceImpl.jouerTour(new DecisionDTO(decision, null), idPartie));
            } else {
                historiqueDecision.put(idPartie, new DecisionDTO(decision, null));
            }
        } else {
            if (partie.getJoueur1() instanceof JoueurBot) {
                sseService.sendEvent(idPartie, partieServiceImpl.jouerTour(new DecisionDTO(null, decision), idPartie));
            } else {
                historiqueDecision.put(idPartie, new DecisionDTO(null, decision));
            }
        }
    }

    public void ajouterDecision(int idPartie, DecisionInDTO decisionInDTO) {
        int idJoueur = decisionInDTO.idJoueur();
        Decision decision = decisionInDTO.decision();

        if (historiqueDecision.containsKey(idPartie)) {
            traiterDecisionDejaPresente(idPartie, idJoueur, decision);
        } else {
            traiterNouvelleDecision(idPartie, idJoueur, decision);
        }
    }
}
