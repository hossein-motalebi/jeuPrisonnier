package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieToujoursCooperer implements Strategie {
    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        return Decision.COOPERER;
    }
}
