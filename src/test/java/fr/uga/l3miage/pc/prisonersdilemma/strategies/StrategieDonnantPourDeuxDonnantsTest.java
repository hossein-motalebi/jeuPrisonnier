package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategieDonnantPourDeuxDonnantsTest {

    @Test
    void testMoinsDeDeuxToursJoueAleatoirementCoopere() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextBoolean()).thenReturn(true); // Retourne true, donc Decision.COOPERER

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    void testMoinsDeDeuxToursJoueAleatoirementTrahir() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextBoolean()).thenReturn(false); // Retourne false, donc Decision.TRAHIR

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testAdversaireRepeteCooperation() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réciprocité.");
    }

    @Test
    void testAdversaireRepeteTrahison() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réciprocité.");
    }

    @Test
    void testAdversaireChangeDecisionJoueAleatoirementCoopere() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextBoolean()).thenReturn(true); // Retourne true, donc Decision.COOPERER

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    void testAdversaireChangeDecisionJoueAleatoirementTrahir() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextBoolean()).thenReturn(false); // Retourne false, donc Decision.TRAHIR

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testAdversaireRepeteCooperationApresChangement() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[4];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réciprocité après que l'adversaire a coopéré deux fois de suite.");
    }

    @Test
    void testAdversaireRepeteTrahisonApresChangement() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[4];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[3] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réciprocité après que l'adversaire a trahi deux fois de suite.");
    }
}

