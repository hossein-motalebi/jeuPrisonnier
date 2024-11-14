package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import java.util.Random;

public class StrategieAleatoire implements Strategie {
    private final Random random = new Random();

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        return random.nextBoolean() ? Decision.TRAHIR : Decision.COOPERER;
    }
}
