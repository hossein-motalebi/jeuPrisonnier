package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieDonnantDonnantSoupconneux implements Strategie {

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours == null || tours.length == 0) {
            return Decision.TRAHIR;
        } else {
            Tour dernierTour = tours[tours.length - 1];
            return dernierTour.getDecisionJoueur(idAdversaire);
        }
    }
}
