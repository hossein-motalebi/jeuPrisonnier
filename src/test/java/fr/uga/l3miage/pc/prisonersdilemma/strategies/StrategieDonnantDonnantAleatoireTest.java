package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StrategieDonnantDonnantAleatoireTest {

    @Test
    public void testJoueAleatoirement() throws Exception {
        // Créer un mock de Random
        Random mockRandom = mock(Random.class);

        // Configurer le comportement du mockRandom
        when(mockRandom.nextDouble()).thenReturn(0.05); // Moins de 0.1, donc joue aléatoirement
        when(mockRandom.nextBoolean()).thenReturn(false); // Retourne true, donc Decision.TRAHIR

        // Créer une instance de la stratégie
        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        // Utiliser la réflexion pour injecter le mockRandom dans la stratégie
        Field randomField = StrategieDonnantDonnantAleatoire.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(strategie, mockRandom);

        // Historique des tours
        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);

        // Appeler la méthode à tester
        Decision decision = strategie.deciderTour(tours);

        // Vérifier la décision
        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait jouer aléatoirement et trahir.");
    }

    @Test
    public void testNeJouePasAleatoirement() throws Exception {
        Random mockRandom = mock(Random.class);

        when(mockRandom.nextDouble()).thenReturn(0.15); // Plus de 0.1, donc ne joue pas aléatoirement

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        Field randomField = StrategieDonnantDonnantAleatoire.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(strategie, mockRandom);

        Tour[] tours = new Tour[1];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait reproduire le dernier coup de l'adversaire.");
    }

    @Test
    public void testPremierTourNeJouePasAleatoirement() throws Exception {
        Random mockRandom = mock(Random.class);

        when(mockRandom.nextDouble()).thenReturn(0.15); // Plus de 0.1, donc ne joue pas aléatoirement

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        Field randomField = StrategieDonnantDonnantAleatoire.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(strategie, mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer par défaut au premier tour.");
    }

    @Test
    public void testPremierTourJoueAleatoirement() throws Exception {
        Random mockRandom = mock(Random.class);

        when(mockRandom.nextDouble()).thenReturn(0.05); // Moins de 0.1, donc joue aléatoirement
        when(mockRandom.nextBoolean()).thenReturn(true); // Retourne false, donc Decision.COOPERER

        StrategieDonnantDonnantAleatoire strategie = new StrategieDonnantDonnantAleatoire();

        Field randomField = StrategieDonnantDonnantAleatoire.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(strategie, mockRandom);

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait jouer aléatoirement et coopérer.");
    }
}
