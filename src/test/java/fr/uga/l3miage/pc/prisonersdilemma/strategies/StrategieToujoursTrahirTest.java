package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategieToujoursTrahirTest {

    @Test
    void testToujoursTrahirSansHistorique() {
        Strategie strategie = new StrategieToujoursTrahir();
        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueCooperation() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[2] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, même si l'adversaire a toujours coopéré.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueTrahison() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, même si l'adversaire a toujours trahi.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueMixte() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] tours = new Tour[4];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[3] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, quel que soit l'historique.");
    }
}

