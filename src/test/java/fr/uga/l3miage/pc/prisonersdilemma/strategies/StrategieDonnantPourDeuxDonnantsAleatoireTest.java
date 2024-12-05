package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategieDonnantPourDeuxDonnantsAleatoireTest {

    @Test
    void testMoinsDeDeuxToursJoueAleatoirementCoopere() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.15);
        when(mockRandom.nextBoolean()).thenReturn(true); // Decision.COOPERER

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    void testMoinsDeDeuxToursJoueAleatoirementTrahir() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.15);
        when(mockRandom.nextBoolean()).thenReturn(false); // Decision.TRAHIR

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testJoueAleatoirementEnFonctionProbabiliteCoopere() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(true); // Decision.COOPERER

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    void testJoueAleatoirementEnFonctionProbabiliteTrahir() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(false); // Decision.TRAHIR

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testAdversaireRepeteCooperation() {
        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer en réciprocité.");
    }

    @Test
    void testAdversaireRepeteTrahison() {
        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir en réciprocité.");
    }

    @Test
    void testAdversaireChangeDecisionJoueAleatoirementCoopere() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.15);
        when(mockRandom.nextBoolean()).thenReturn(true); // Decision.COOPERER

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        // Historique où l'adversaire a changé de décision
        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }

    @Test
    void testAdversaireChangeDecisionJoueAleatoirementTrahir() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.15); // Plus de 0.1, donc ne joue pas aléatoirement sur la probabilité
        when(mockRandom.nextBoolean()).thenReturn(false); // Decision.TRAHIR

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[2];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testJoueAleatoirementProbabiliteEtAdversaireChange() {
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextDouble()).thenReturn(0.05);
        when(mockRandom.nextBoolean()).thenReturn(true); // Decision.COOPERER

        StrategieDonnantPourDeuxDonnantsAleatoire strategie = new StrategieDonnantPourDeuxDonnantsAleatoire();
        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }
}
