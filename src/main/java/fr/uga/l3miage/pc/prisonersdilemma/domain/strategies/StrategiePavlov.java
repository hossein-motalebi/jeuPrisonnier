package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.ResultatTour;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;


public class StrategiePavlov implements Strategie {

    private SecureRandom random;

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }

    public StrategiePavlov() {
        this.setRandom(new SecureRandom());
    }

    public Decision deciderTour(Tour[] tours , int idJoueur , int idAdversaire) {
        if (tours.length == 0) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }
        ResultatTour dernierResultat = tours[tours.length - 1].getGainJoueur(idJoueur);
        if (dernierResultat == ResultatTour.TENTATION ||
                dernierResultat == ResultatTour.RECOMPENSE)
            return tours[tours.length -1].getDecisionJoueur(idJoueur);

        return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;


    }
}