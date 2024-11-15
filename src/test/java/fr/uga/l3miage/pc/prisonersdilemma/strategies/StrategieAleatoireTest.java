package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StrategieAleatoireTest {

    @Test
    void testDistributionAleatoireDesDecisions() {
        Strategie strategie = new StrategieAleatoire();

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

        // Calcul des ratios
        double coopererRatio = (double) coopererCount / iterations;
        double trahirRatio = (double) trahirCount / iterations;

        // Marges acceptables (5% d'écart)
        double marge = 0.05;

        assertTrue(coopererRatio >= 0.5 - marge && coopererRatio <= 0.5 + marge,
                "Le ratio de coopération devrait être proche de 50%.");

        assertTrue(trahirRatio >= 0.5 - marge && trahirRatio <= 0.5 + marge,
                "Le ratio de trahison devrait être proche de 50%.");
    }
}
