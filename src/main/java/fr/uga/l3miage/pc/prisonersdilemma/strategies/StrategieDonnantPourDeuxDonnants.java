package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import java.util.Random;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieDonnantPourDeuxDonnants implements Strategie {
    private Random random = new Random();

    protected void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours == null || tours.length < 2) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        } else {
            Tour avantDernierTour = tours[tours.length - 2];
            Tour dernierTour = tours[tours.length - 1];

            Decision avantDerniereDecisionAdversaire = avantDernierTour.getDecisionJoueur(idAdversaire);
            Decision derniereDecisionAdversaire = dernierTour.getDecisionJoueur(idAdversaire);

            if (avantDerniereDecisionAdversaire == derniereDecisionAdversaire) {
                return derniereDecisionAdversaire;
            } else {
                return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            }
        }
    }
}
