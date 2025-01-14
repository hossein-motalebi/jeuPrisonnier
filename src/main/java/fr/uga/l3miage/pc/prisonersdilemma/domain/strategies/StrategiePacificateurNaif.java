package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;


public class StrategiePacificateurNaif implements Strategie {

    private SecureRandom random;

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }
    public boolean parFois(){
        return this.random.nextInt(100) > 75;
    }
    public StrategiePacificateurNaif() {
        this.setRandom(new SecureRandom());
    }

    public Decision deciderTour(Tour[] tours , int idJoueur , int idAdversaire) {
        if (tours.length == 0) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }
        Decision decision=tours[tours.length -1].getDecisionJoueur(idAdversaire);
        if (decision==Decision.COOPERER)
            return Decision.COOPERER;
        if (parFois())
            return Decision.COOPERER;
        return Decision.TRAHIR;


    }
}