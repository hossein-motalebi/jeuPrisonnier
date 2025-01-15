package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import java.security.SecureRandom;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

public class StrategieDonnantPourDeuxDonnants implements Strategie {
    private SecureRandom random = new SecureRandom();

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours.length < 2)
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;

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
