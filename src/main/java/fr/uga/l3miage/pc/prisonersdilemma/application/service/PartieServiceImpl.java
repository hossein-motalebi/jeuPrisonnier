package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.domain.port.in.PartieUseCases;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurHumain;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Partie;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.AbandonnerDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.mappers.OutPartieDtoMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionDTO;

@Getter
@Service
public class PartieServiceImpl implements PartieUseCases { //NOSONAR

    private Partie partie;

    private final JoueurServiceImpl joueurServiceImpl;

    public PartieServiceImpl(JoueurServiceImpl joueurServiceImpl){
        this.joueurServiceImpl = joueurServiceImpl;
    }

    public OutPartieDTO demarrerPartie(InitPartieDTO initPartieDTO) {
        joueurServiceImpl.setCompteurJoueur(1);
        Joueur joueur1= joueurServiceImpl.creerUser(initPartieDTO.getNomJoueur1(),initPartieDTO.isJoueur1bot(),initPartieDTO.getStrategieJoueur1()
        , initPartieDTO.isStrategieExterneJoueur1());
        Joueur joueur2= joueurServiceImpl.creerUser(initPartieDTO.getNomJoueur2(),initPartieDTO.isJoueur2bot(),initPartieDTO.getStrategieJoueur2()
        , initPartieDTO.isStrategieExterneJoueur2());
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

    public OutPartieDTO abandonner(AbandonnerDTO abandonnerDTO) {
        Joueur joueur= (abandonnerDTO.idPlayer()==1) ? partie.getJoueur1() : partie.getJoueur2();
        JoueurBot newJoueur = joueurServiceImpl.transformerUser(
                        (JoueurHumain)joueur ,
                        abandonnerDTO.strategie(),
                        abandonnerDTO.strategieExterne());
        if (abandonnerDTO.idPlayer() == 1) {
            partie.setJoueur1(newJoueur);
        } else {
            partie.setJoueur2(newJoueur);
        }

        return OutPartieDtoMapper.map(partie);

    }


}
