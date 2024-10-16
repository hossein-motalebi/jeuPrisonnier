package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.ResultatTour;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;

import java.util.List;

public class StrategieDonnantDonnant implements Strategie {

    @Override
    public Decision prendreDecision(List<ResultatTour> historique) {
        if (historique.isEmpty()) {
            return Decision.COOPERER;
        } else {
            ResultatTour dernierTour = historique.get(historique.size() - 1);

            /*
            return dernierTour.getDecisionJoueur1();
             */
            return Decision.TRAHIR;

        }
    }
}
