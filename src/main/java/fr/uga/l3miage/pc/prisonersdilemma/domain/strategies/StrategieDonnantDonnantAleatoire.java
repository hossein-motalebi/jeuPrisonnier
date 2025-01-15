package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.security.SecureRandom;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

public class StrategieDonnantDonnantAleatoire implements Strategie {
    private SecureRandom random = new SecureRandom();
    private static final double PROBABILITE_ALEATOIRE = 0.1; // 10% de chance de jouer aléatoirement

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (random.nextDouble() < PROBABILITE_ALEATOIRE) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {
            if (tours == null || tours.length == 0) {
                // Coopérer par défaut
                return Decision.COOPERER;
            } else {
                Tour dernierTour = tours[tours.length - 1];
                return dernierTour.getDecisionJoueur(idAdversaire);
            }
        }
    }
}
