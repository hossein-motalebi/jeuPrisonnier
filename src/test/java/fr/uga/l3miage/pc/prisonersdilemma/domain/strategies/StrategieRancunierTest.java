package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.Test;

class StrategieRancunierTest {

    @Test
     void testCooperationInitiale() {
        StrategieRancunier strategie = new StrategieRancunier();

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer au premier tour.");
    }

    @Test
    void testContinueCooperationSiAdversaireCoopere() {
        StrategieRancunier strategie = new StrategieRancunier();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait continuer à coopérer si l'adversaire n'a pas trahi.");
    }

    @Test
    void testTrahisonApresTrahisonAdversaire() {
        StrategieRancunier strategie = new StrategieRancunier();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir après que l'adversaire a trahi.");
    }

    @Test
    void testToujoursTrahirApresTrahisonAdversaire() {
        StrategieRancunier strategie = new StrategieRancunier();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.COOPERER)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait continuer à trahir après que l'adversaire a trahi.");
    }
}