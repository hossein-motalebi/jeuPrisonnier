package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;

import static org.junit.jupiter.api.Assertions.*;

class StrategieDonnantDonnantTest {

    @Test
    void testPremierTourAleatoire() {
        Strategie strategie = new StrategieDonnantDonnant();

        Tour[] tours = new Tour[0];

        int coopererCount = 0;
        int trahirCount = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            Decision decision = strategie.deciderTour(tours,1,2);
            if (decision == Decision.COOPERER) {
                coopererCount++;
            } else if (decision == Decision.TRAHIR) {
                trahirCount++;
            }
        }

        assertTrue(coopererCount > 0, "La stratégie devrait parfois coopérer au premier tour.");
        assertTrue(trahirCount > 0, "La stratégie devrait parfois trahir au premier tour.");
    }

    @Test
    void testApresCooperation() {
        Strategie strategie = new StrategieDonnantDonnant();

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer après que l'adversaire a coopéré.");
    }

    @Test
    void testApresTrahison() {
        Strategie strategie = new StrategieDonnantDonnant();

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir après que l'adversaire a trahi.");
    }

    @Test
    void testAvecHistoriqueMixte() {
        Strategie strategie = new StrategieDonnantDonnant();

        Tour[] tours = new Tour[4];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait reproduire la dernière décision de l'adversaire.");
    }

    @Test
    void testApresLongueCooperationPuisTrahison() {
        Strategie strategie = new StrategieDonnantDonnant();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[4] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir après une trahison inattendue de l'adversaire.");
    }

    @Test
    void testApresLongueTrahisonPuisCooperation() {
        Strategie strategie = new StrategieDonnantDonnant();

        // Historique de trahisons suivies d'une coopération
        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[3] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[4] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours,1,2);

        // La stratégie devrait coopérer
        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer après une coopération de l'adversaire.");
    }
}
