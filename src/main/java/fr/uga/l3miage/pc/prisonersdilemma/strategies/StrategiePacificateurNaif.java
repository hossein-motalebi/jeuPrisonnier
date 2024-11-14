package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import java.util.Random;


public class StrategiePacificateurNaif implements Strategie {

    private Random random;

    protected void setRandom(Random random) {
        this.random = random;
    }
    public boolean parFois(){
        return this.random.nextInt(100) > 75;
    }
    public StrategiePacificateurNaif() {
        this.setRandom(new Random());
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