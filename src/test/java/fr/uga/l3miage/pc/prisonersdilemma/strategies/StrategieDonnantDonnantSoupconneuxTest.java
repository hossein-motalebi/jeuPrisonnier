package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategieDonnantDonnantSoupconneuxTest {

    @Test
    void testPremierTourTrahir() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir au premier tour.");
    }

    @Test
    void testDeuxiemeTourAdversaireCoopere() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réponse à la coopération de l'adversaire.");
    }

    @Test
    void testDeuxiemeTourAdversaireTrahit() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réponse à la trahison de l'adversaire.");
    }

    @Test
    void testTroisiemeTourAdversaireCooperePuisTrahit() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réponse à la trahison de l'adversaire.");
    }

    @Test
    void testTroisiemeTourAdversaireTrahitPuisCoopere() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réponse à la coopération de l'adversaire.");
    }

    @Test
    void testLongueSequenceAlternance() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[4] = new Tour(Decision.TRAHIR, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réponse à la coopération de l'adversaire.");
    }

    @Test
    void testLongueSequenceAdversaireCoopere() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[4] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait continuer à coopérer en réponse à la coopération de l'adversaire.");
    }

    @Test
    void testLongueSequenceAdversaireTrahit() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[3] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[4] = new Tour(Decision.TRAHIR, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait continuer à trahir en réponse à la trahison de l'adversaire.");
    }

    @Test
    void testAdversaireCoopereApresTrahisonProlongee() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[1] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[2] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tours[3] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[4] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réponse à la coopération de l'adversaire.");
    }

    @Test
    void testAdversaireTrahitApresCooperationProlongee() {
        StrategieDonnantDonnantSoupconneux strategie = new StrategieDonnantDonnantSoupconneux();

        Tour[] tours = new Tour[5];
        tours[0] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[4] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réponse à la trahison de l'adversaire.");
    }
}
