package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.security.SecureRandom;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieDonnantPourDeuxDonnantsAleatoire implements Strategie {
    private SecureRandom random = new SecureRandom();
    private static final double PROBABILITE_ALEATOIRE = 0.1; // 10% de chance de jouer aléatoirement

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (random.nextDouble() < PROBABILITE_ALEATOIRE || tours.length < 2) {
            // Jouer un coup aléatoire
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {
            Tour avantDernierTour = tours[tours.length - 2];
            Tour dernierTour = tours[tours.length - 1];

            Decision avantDerniereDecisionAdversaire = avantDernierTour.getDecisionJoueur(idAdversaire);
            Decision derniereDecisionAdversaire = dernierTour.getDecisionJoueur(idAdversaire);

            if (avantDerniereDecisionAdversaire == derniereDecisionAdversaire) {
                // Répété de l'adversaire
                return derniereDecisionAdversaire;
            } else {
                // Jouer aléatoirement
                return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            }
        }
    }
}
