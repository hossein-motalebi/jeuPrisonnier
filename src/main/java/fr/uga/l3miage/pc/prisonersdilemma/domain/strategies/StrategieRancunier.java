package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

public class StrategieRancunier implements Strategie {

    private boolean adversaireATrahit = false;

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (!adversaireATrahit && tours != null) {
            for (Tour tour : tours) {
                if (tour.getDecisionJoueur(idAdversaire) == Decision.TRAHIR) {
                    adversaireATrahit = true;
                    break;
                }
            }
        }

        if (adversaireATrahit) {
            return Decision.TRAHIR;
        } else {
            return Decision.COOPERER;
        }
    }
}