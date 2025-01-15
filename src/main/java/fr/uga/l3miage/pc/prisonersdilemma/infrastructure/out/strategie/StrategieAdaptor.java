package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.strategie;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.factory.StrategieExterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import fr.uga.strats.g8.enums.TypeAction;

import java.util.ArrayList;
import java.util.List;

//vu que "Strategie" package externe est une class abstrait il n'y a pas de confusion pour compiler
public class StrategieAdaptor implements Strategie {

    fr.uga.strats.g8.strategie.Strategie strategieExtern;

    public StrategieAdaptor(TypeStrategie strategie) {
        strategieExtern = StrategieExterneFactory.creeStrategieExtern(strategie);
    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        List<TypeAction> lst = arrayToListConverter(tours, idAdversaire);
        int dernierResultat;
        if (tours.length == 0) {
            dernierResultat = 0;
        }
        else{
            dernierResultat=(idAdversaire == 1) ? tours[tours.length - 1].getGainJoueur1().getPoints()
                : tours[tours.length - 1].getGainJoueur2().getPoints();
        }
        TypeAction action = strategieExtern.getAction(lst, dernierResultat);
        return action == TypeAction.COOPERER ? Decision.COOPERER : Decision.TRAHIR;
    }

    protected List<TypeAction> arrayToListConverter(Tour[] historique, int idAdversaire) {
        List<TypeAction> lst = new ArrayList<>(historique.length);
        for (Tour tour : historique) {
            Decision decision = (idAdversaire == 1) ? tour.getDecisionJoueur1() : tour.getDecisionJoueur2();
            lst.add((decision == Decision.COOPERER) ? TypeAction.COOPERER : TypeAction.TRAHIR);
        }
        return lst;
    }

}
