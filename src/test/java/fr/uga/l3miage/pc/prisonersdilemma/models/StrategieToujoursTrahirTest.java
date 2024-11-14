package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.StrategieToujoursTrahir;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategieToujoursTrahirTest {

    @Test
    void testToujoursTrahirSansHistorique() {
        Strategie strategie = new StrategieToujoursTrahir();
        Tour[] historique = new Tour[0];

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueCooperation() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] historique = new Tour[3];
        historique[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        historique[1] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        historique[2] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, même si l'adversaire a toujours coopéré.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueTrahison() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] historique = new Tour[3];
        historique[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        historique[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        historique[2] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, même si l'adversaire a toujours trahi.");
    }

    @Test
    void testToujoursTrahirAvecHistoriqueMixte() {
        Strategie strategie = new StrategieToujoursTrahir();

        Tour[] historique = new Tour[4];
        historique[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        historique[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        historique[2] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        historique[3] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait toujours trahir, quel que soit l'historique.");
    }
}

