package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.ResultatTourEntity;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;

import java.util.List;
import java.util.Random;

public class StrategieAleatoire implements Strategie {
    private final Random random = new Random();

    @Override
    public Decision prendreDecision(List<ResultatTourEntity> historique) {
        return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
    }
}
