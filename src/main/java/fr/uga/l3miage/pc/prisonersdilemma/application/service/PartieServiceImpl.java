package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.EtatPartie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurHumain;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.*;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.mappers.OutPartieDtoMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
public class PartieServiceImpl implements PartieUseCases { // NOSONAR

    private Map<Integer, Partie> parties;
    private AtomicInteger idCounter;
    private final JoueurUseCases joueurServiceImpl;
    private final DecisionUseCases decisionServiceImpl;
    private SseService sseService;

    public PartieServiceImpl(JoueurServiceImpl joueurServiceImpl, SseService sseService,
            DecisionServiceImpl decisionServiceImpl) {

        this.decisionServiceImpl = decisionServiceImpl;
        this.joueurServiceImpl = joueurServiceImpl;
        this.parties = new HashMap<>();
        this.idCounter = new AtomicInteger(10000);
        this.sseService = sseService;

    }

    public int initPartie(InitPartieInDTO initPartieInDTO) {
        int idPartie = idCounter.getAndIncrement();
        Joueur joueur1 = joueurServiceImpl.creerUser(initPartieInDTO.getNomJoueur(), 1);
        Joueur joueur2 = joueurServiceImpl.creerUser("En attend", 2);
        parties.put(idPartie, new Partie(initPartieInDTO.getNbMaxTours(), joueur1, joueur2));
        return idPartie;

    }

    @Override
    public void joinPartie(int idPartie, JoinPartieRequestDTO joinPartieRequestDTO) {
        Partie partie = parties.get(idPartie);
        if (partie == null) {
            throw new IllegalArgumentException("La partie n'existe pas");
        }
        partie.getJoueur2().setNom(joinPartieRequestDTO.nomJoueur());
        partie.setEtatPartie(EtatPartie.ENCOURS);
        // en envoie les dto partie à la vue en utilisant SSE
        OutPartieDTO outPartieDTO = OutPartieDtoMapper.map(partie);
        sseService.sendEvent(idPartie, outPartieDTO);
    }

    public OutPartieDTO jouerTour(DecisionDTO decisionDTO, int idPartie) {
        Partie partie = parties.get(idPartie);
        Decision decision1 = decisionDTO.decisionJoueur1();
        Decision decision2 = decisionDTO.decisionJoueur2();
        Joueur joueur1 = partie.getJoueur1();
        Joueur joueur2 = partie.getJoueur2();

        // on veut être sûr que la decision d'un bot n'était pas venu depuis UI
        if (decision1 != null && joueur1 instanceof JoueurBot) {
            decision1 = null;
        }
        if (decision2 != null && joueur2 instanceof JoueurBot) {
            decision2 = null;
        }

        if (decision1 == null && joueur1 instanceof JoueurHumain) {
            throw new IllegalArgumentException("Le joueur 1 n'a pas fait de choix");
        } else if (decision1 == null && joueur1 instanceof JoueurBot joueurBot) { // on utilise startegie pour joueur 1
            decision1 = joueurBot.prendreDecision(partie.getToursCopy());
        }
        if (decision2 == null && joueur2 instanceof JoueurHumain) {
            throw new IllegalArgumentException("Le joueur 2 n'a pas fait de choix");
        } else if (decision2 == null && joueur2 instanceof JoueurBot joueurBot) { // on utilise startegie pour joueur 2
            decision2 = joueurBot.prendreDecision(partie.getToursCopy());
        }

        partie.initTour(decision1, decision2);
        partie.finishTour();

        return OutPartieDtoMapper.map(partie);
    }

    public void abandonner(int idPartie, AbandonnerDTO abandonnerDTO) {
        Partie partie = parties.get(idPartie);
        Joueur joueur = (abandonnerDTO.idPlayer() == 1) ? partie.getJoueur1() : partie.getJoueur2();
        JoueurBot newJoueur = joueurServiceImpl.transformerUser(
                (JoueurHumain) joueur,
                abandonnerDTO.strategie(),
                abandonnerDTO.strategieExterne());
        if (abandonnerDTO.idPlayer() == 1) {
            partie.setJoueur1(newJoueur);
        } else {
            partie.setJoueur2(newJoueur);
        }

        sseService.sendEvent(idPartie, OutPartieDtoMapper.map(partie));
        decisionServiceImpl.verifieDecisionEnAbandonne(idPartie, abandonnerDTO.idPlayer());

    }

    public List<PartieEnAttendDTO> getPartiesEnAttend() {

        List<PartieEnAttendDTO> partiesEnAttente = new ArrayList<>();
        parties.forEach((key, value) -> {
            if (value.getEtatPartie() == EtatPartie.INIT) {
                partiesEnAttente.add(new PartieEnAttendDTO(key, value.getJoueur1().getNom()));
            }
        });
        return partiesEnAttente;

    }

}