package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;

public class StrategieSondeurNaif implements Strategie {


        private SecureRandom random;

        protected void setRandom(SecureRandom random) {
            this.random = random;
        }
        public boolean parFois(){
            return this.random.nextInt(100) > 75;
        }
        public StrategieSondeurNaif() {
            this.setRandom(new SecureRandom());
        }

        public Decision deciderTour(Tour[] tours , int idJoueur , int idAdversaire) {
            if (tours.length == 0) {
                return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            }
            Decision decision=tours[tours.length -1].getDecisionJoueur(idAdversaire);
            if (decision==Decision.TRAHIR)
                return Decision.TRAHIR;
            if (parFois()) // cooperer
                return Decision.TRAHIR;
            return Decision.COOPERER;

        }
    }

