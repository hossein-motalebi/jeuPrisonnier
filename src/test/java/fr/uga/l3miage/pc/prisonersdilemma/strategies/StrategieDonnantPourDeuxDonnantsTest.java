package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StrategieDonnantPourDeuxDonnantsTest {

    @Test
    public void testMoinsDeDeuxToursJoueAleatoirementCoopere() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true); // Retourne true, donc Decision.COOPERER

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    public void testMoinsDeDeuxToursJoueAleatoirementTrahir() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(false); // Retourne false, donc Decision.TRAHIR

        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    public void testAdversaireRepeteCooperation() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réciprocité.");
    }

    @Test
    public void testAdversaireRepeteTrahison() {
        StrategieDonnantPourDeuxDonnants strategie = new StrategieDonnantPourDeuxDonnants();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réciprocité.");
    }

    @Test
    public void testAdversaireChangeDecisionJoueAleatoirementCoopere() {
        Random mockRandom = mock(Random.class);
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
    public void testAdversaireChangeDecisionJoueAleatoirementTrahir() {
        Random mockRandom = mock(Random.class);
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
    public void testAdversaireRepeteCooperationApresChangement() {
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
    public void testAdversaireRepeteTrahisonApresChangement() {
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

