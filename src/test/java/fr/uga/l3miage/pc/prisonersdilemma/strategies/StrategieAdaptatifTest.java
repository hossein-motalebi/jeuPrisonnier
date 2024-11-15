package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class StrategieAdaptatifTest {

    @Test
     void testSequenceInitiale() {
        StrategieAdaptatif strategie = new StrategieAdaptatif();

        Tour[] tours = new Tour[0];

        Decision[] sequenceAttendue = {
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR
        };

        for (int i = 0; i < 11; i++) {
            Decision decision = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decision, "La décision au tour " + (i + 1) + " devrait être " + sequenceAttendue[i]);

            Tour nouveauTour = new Tour(decision, Decision.COOPERER);
            tours = ajouterTour(tours, nouveauTour);
        }
    }

    @Test
     void testChoixApresSequenceInitialeTrahirMeilleur() {
        StrategieAdaptatif strategie = new StrategieAdaptatif();

        Tour[] tours = new Tour[11];

        Decision[] sequenceInitiale = {
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR
        };

        for (int i = 0; i < 11; i++) {
            Decision decision = sequenceInitiale[i];
            Decision decisionAdversaire = (i < 6) ? Decision.TRAHIR : Decision.COOPERER;

            Tour nouveauTour = new Tour(decision, decisionAdversaire);
            tours[i] = nouveauTour;
        }

        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait trahir car trahir a donné un meilleur gain moyen.");



    }

    private Tour[] ajouterTour(Tour[] tours, Tour nouveauTour) {
        Tour[] nouveauxTours = new Tour[tours.length + 1];
        System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);
        nouveauxTours[tours.length] = nouveauTour;
        return nouveauxTours;
    }
}
