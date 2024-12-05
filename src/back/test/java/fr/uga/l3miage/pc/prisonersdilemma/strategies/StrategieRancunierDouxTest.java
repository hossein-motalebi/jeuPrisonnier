package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

public class StrategieRancunierDouxTest {

    @Test
    public void testCooperationInitiale() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer au premier tour.");
    }

    @Test
    public void testPunitionApresTrahisonAdversaire() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = { new Tour(Decision.COOPERER, Decision.COOPERER) };
        strategie.deciderTour(tours, 1, 2);

        tours = new Tour[] {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait commencer la punition par trahir.");


        Decision[] sequenceAttendue = {
                Decision.TRAHIR, // Tour 2
                Decision.TRAHIR, // Tour 3
                Decision.TRAHIR, // Tour 4
                Decision.TRAHIR, // Tour 5
                Decision.COOPERER, // Tour 6
                Decision.COOPERER // Tour 7
        };

        for (int i = 0; i < sequenceAttendue.length; i++) {

            Tour[] nouveauxTours = new Tour[tours.length + 1];
            System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);

            nouveauxTours[tours.length] = new Tour(null, Decision.COOPERER);
            tours = nouveauxTours;

            Decision decisionPunition = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decisionPunition, "La décision au tour " + (i + 2) + " devrait être " + sequenceAttendue[i]);
        }

        Tour[] toursApresPunition = new Tour[tours.length + 1];
        System.arraycopy(tours, 0, toursApresPunition, 0, tours.length);
        toursApresPunition[tours.length] = new Tour(null, Decision.COOPERER);
        Decision decisionApresPunition = strategie.deciderTour(toursApresPunition, 1, 2);
        assertEquals(Decision.COOPERER, decisionApresPunition, "La stratégie devrait coopérer après la punition si l'adversaire coopère.");
    }

    @Test
    public void testRepunitionSiAdversaireRetrahit() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };

        for (int i = 0; i < 6; i++) {

            Tour[] nouveauxTours = new Tour[tours.length + 1];
            System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);
            nouveauxTours[tours.length] = new Tour(null, Decision.COOPERER);
            tours = nouveauxTours;
            strategie.deciderTour(tours, 1, 2);
        }


        Tour[] nouveauxTours = new Tour[tours.length + 1];
        System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);
        nouveauxTours[tours.length] = new Tour(null, Decision.TRAHIR);
        tours = nouveauxTours;

        Decision decisionRepunition = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decisionRepunition, "La stratégie devrait recommencer la punition après une nouvelle trahison de l'adversaire.");
    }
}
