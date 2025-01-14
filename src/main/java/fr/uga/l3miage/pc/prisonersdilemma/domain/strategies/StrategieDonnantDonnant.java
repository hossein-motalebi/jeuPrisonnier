package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;

public class StrategieDonnantDonnant implements Strategie {

    private final SecureRandom random = new SecureRandom();

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours.length == 0) {

            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {

            Tour dernierTour = tours[tours.length - 1];
            return dernierTour.getDecisionJoueur(idAdversaire);
        }
    }

}