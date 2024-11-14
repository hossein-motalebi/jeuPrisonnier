package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import java.util.Random;

public class StrategieDonnantDonnant implements Strategie {

    private final Random random = new Random();

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours.length == 0) {

            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {

            Tour dernierTour = tours[tours.length - 1];
            Decision derniereDecisionAdversaire = dernierTour.getDecisionJoueur(2);
            if (derniereDecisionAdversaire == null) {

                return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            }
            return derniereDecisionAdversaire;
        }
    }

}