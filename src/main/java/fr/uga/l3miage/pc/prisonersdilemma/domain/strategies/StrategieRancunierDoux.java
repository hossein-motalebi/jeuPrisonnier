package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

public class StrategieRancunierDoux implements Strategie {

    private boolean adversaireATrahit = false;
    private int indexPunition = -1;

    private static final Decision[] SEQUENCE_PUNITION = {
            Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.COOPERER, Decision.COOPERER
    };

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {

        if (!adversaireATrahit && tours.length > 0) {
            Tour tour = tours[tours.length-1];
                if (tour.getDecisionJoueur(idAdversaire) == Decision.TRAHIR) {
                    adversaireATrahit = true;
                    indexPunition = 0;
                }

        }

        if (indexPunition >= 0 && indexPunition < SEQUENCE_PUNITION.length) {
            Decision decision = SEQUENCE_PUNITION[indexPunition];
            indexPunition++;
            if (indexPunition == SEQUENCE_PUNITION.length) {
                adversaireATrahit = false;
                indexPunition = -1;
            }
            return decision;
        } else {
            return Decision.COOPERER;
        }
    }
}