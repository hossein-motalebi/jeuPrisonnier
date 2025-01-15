package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

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
