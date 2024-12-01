package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import org.springframework.stereotype.Service;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;



@Service
public class PartieService { //NOSONAR

    private Partie partie;

    private final JoueurService joueurService;

    public PartieService(JoueurService joueurService){
        this.joueurService=joueurService;
    }

    public OutPartieDTO demarrerPartie(InitPartieDTO initPartieDTO) {
        Joueur joueur1=joueurService.creerUser(initPartieDTO.getNomJoueur1(),initPartieDTO.isJoueur1bot(),initPartieDTO.getStrategieJoueur1());
        Joueur joueur2=joueurService.creerUser(initPartieDTO.getNomJoueur2(),initPartieDTO.isJoueur2bot(),initPartieDTO.getStrategieJoueur2());
        this.partie=new Partie(initPartieDTO.getNbMaxTours(),joueur1,joueur2);
        return OutPartieDtoMapper.map(partie);

    }

    public Partie joueurTour(Decision decision1, Decision decision2) {
        partie.initTour(decision1, decision2);
        partie.finishTour();
        return partie;
    }








    /* TODO //NOSONAR
    //Gérer le déroulement d'une partie du jeu.
    //Coordonner les interactions entre les joueurs.
    //Maintenir l'état de la partie en cours.
    //Appliquer les règles du jeu pour chaque tour.
    //Calculer les scores des joueurs.
     */

}
