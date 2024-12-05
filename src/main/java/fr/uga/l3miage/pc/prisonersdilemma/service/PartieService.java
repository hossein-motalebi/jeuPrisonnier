package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import lombok.Getter;
import org.springframework.stereotype.Service;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;
import fr.uga.l3miage.pc.prisonersdilemma.dto.DecisionDTO;

@Getter
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

    public OutPartieDTO jouerTour(DecisionDTO decisionDTO) {
        Decision decision1=decisionDTO.decisionJoueur1();
        Decision decision2=decisionDTO.decisionJoueur2();
        Joueur joueur1=partie.getJoueur1();
        Joueur joueur2=partie.getJoueur2();

        //on veut être sûr que la decision d'un bot n'était pas venu depuis UI
        if (decision1 != null && joueur1 instanceof JoueurBot){
            decision1 = null ;
        }
        if (decision2 != null && joueur2 instanceof JoueurBot){
            decision2 = null ;
        }


        if (decision1==null && joueur1 instanceof JoueurHumain){
            throw new IllegalArgumentException("Le joueur 1 n'a pas fait de choix");
        }
        else if (decision1 ==null && joueur1 instanceof JoueurBot joueurBot) { // on utilise startegie pour joueur 1
            decision1=joueurBot.prendreDecision(partie.getToursCopy());
        }
        if (decision2==null && joueur2 instanceof JoueurHumain){
            throw new IllegalArgumentException("Le joueur 2 n'a pas fait de choix");
        }
        else if (decision2 ==null && joueur2 instanceof JoueurBot joueurBot){ // on utilise startegie pour joueur 2
            decision2 = joueurBot.prendreDecision(partie.getToursCopy());
        }

        partie.initTour(decision1, decision2);
        partie.finishTour();

        return OutPartieDtoMapper.map(partie);
    }








    /* TODO //NOSONAR
    //Gérer le déroulement d'une partie du jeu.
    //Coordonner les interactions entre les joueurs.
    //Maintenir l'état de la partie en cours.
    //Appliquer les règles du jeu pour chaque tour.
    //Calculer les scores des joueurs.
     */

}
