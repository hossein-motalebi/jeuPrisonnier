package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategieToujoursTrahirTest {

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

