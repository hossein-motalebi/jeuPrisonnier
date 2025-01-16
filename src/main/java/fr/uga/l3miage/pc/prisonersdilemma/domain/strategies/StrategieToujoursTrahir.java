package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

public class StrategieToujoursTrahir implements Strategie {
    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        return Decision.TRAHIR;
    }
}
