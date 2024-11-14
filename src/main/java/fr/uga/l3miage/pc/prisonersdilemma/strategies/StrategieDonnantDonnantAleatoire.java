package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieDonnantDonnantAleatoire implements Strategie {
    private final Random random = new Random();
    private static final double PROBABILITE_ALEATOIRE = 0.1; // 10% de chance de jouer aléatoirement

    @Override
    public Decision deciderTour(Tour[] tours) {
        if (random.nextDouble() < PROBABILITE_ALEATOIRE) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {
            if (tours == null || tours.length == 0) {
                // Coopérer par défaut
                return Decision.COOPERER;
            } else {
                Tour dernierTour = tours[tours.length - 1];
                return dernierTour.getDecisionJoueur2();
            }
        }
    }
}
