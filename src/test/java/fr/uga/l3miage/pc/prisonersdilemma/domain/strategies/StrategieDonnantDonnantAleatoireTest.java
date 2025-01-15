package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import org.junit.jupiter.api.Test;


import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategieDonnantDonnantAleatoireTest {

    @Test
    void testJoueAleatoirement()  {
        // Créer un mock de Random
        SecureRandom mockRandom = mock(SecureRandom.class);

        // Configurer le comportement du mockRandom
        when(mockRandom.nextDouble()).thenReturn(0.05); // Moins de 0.1, donc joue aléatoirement
        when(mockRandom.nextBoolean()).thenReturn(false); // Retourne true, donc Decision.TRAHIR

        // Créer une instance de la stratégie
        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        // Utiliser le setter protégé pour injecter le mockRandom
        strategie.setRandom(mockRandom);

        // Historique des tours
        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        // Appeler la méthode à tester
        Decision decision = strategie.deciderTour(tours,1,2);

        // Vérifier la décision
        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    void testNeJouePasAleatoirement()  {
        SecureRandom mockRandom = mock(SecureRandom.class);

        when(mockRandom.nextDouble()).thenReturn(0.15); // Plus de 0.1, donc ne joue pas aléatoirement

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait reproduire le dernier coup de l'adversaire.");
    }

    @Test
    void testPremierTourNeJouePasAleatoirement()  {
        SecureRandom mockRandom = mock(SecureRandom.class);

        when(mockRandom.nextDouble()).thenReturn(0.15); // Plus de 0.1, donc ne joue pas aléatoirement

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer par défaut au premier tour.");
    }

    @Test
    void testPremierTourJoueAleatoirement() {
        SecureRandom mockRandom = mock(SecureRandom.class);

        when(mockRandom.nextDouble()).thenReturn(0.05); // Moins de 0.1, donc joue aléatoirement
        when(mockRandom.nextBoolean()).thenReturn(true); // Retourne false, donc Decision.COOPERER

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        strategie.setRandom(mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }
}
