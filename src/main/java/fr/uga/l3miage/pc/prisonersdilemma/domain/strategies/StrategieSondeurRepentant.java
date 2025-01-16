package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;

public class StrategieSondeurRepentant implements Strategie {



        private SecureRandom random;
        private boolean testParfois;

        protected void setRandom(SecureRandom random) {
            this.random = random;
        }
        public boolean parFois(){
            return this.random.nextInt(100) > 75;
        }
        public StrategieSondeurRepentant() {
            this.setRandom(new SecureRandom());
            testParfois = false;
        }


        public Decision deciderTour(Tour[] tours , int idJoueur , int idAdversaire) {
            if (tours.length == 0) {
                return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            }
            Decision decision=tours[tours.length -1].getDecisionJoueur(idAdversaire);
            if (testParfois && decision==Decision.TRAHIR) {
                testParfois = false;
                return Decision.COOPERER;
            }
            testParfois = false;
            if (decision==Decision.TRAHIR)
                return Decision.TRAHIR;
            if (parFois()) { // cooperer
                testParfois = true;
                return Decision.TRAHIR;
            }
            return Decision.COOPERER;

        }
    }

