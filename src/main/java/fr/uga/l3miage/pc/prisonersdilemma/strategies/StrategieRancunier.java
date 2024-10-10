package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.ResultatTour;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;

import java.util.List;

public class StrategieRancunier implements Strategie {
    private boolean aEteTrahi = false;

    @Override
    public Decision prendreDecision(List<ResultatTour> historique) {
        return null;
    }

    /*
    @Override
    public Decision prendreDecision(List<ResultatTourEntity> historique) {
        if (!aEteTrahi) {
            for (ResultatTourEntity tour : historique) {
                if (tour.getDecisionJoueur1() == Decision.TRAHIR) {
                    aEteTrahi = true;
                    break;
                }
            }
        }
        return aEteTrahi ? Decision.TRAHIR : Decision.COOPERER;
    }
     */


}
