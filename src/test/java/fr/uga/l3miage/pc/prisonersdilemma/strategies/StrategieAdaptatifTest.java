package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// A AMELIORER
class StrategieAdaptatifTest {

    // Méthode utilitaire pour ajouter un tour à un tableau de tours
    private Tour[] ajouterTour(Tour[] tours, Tour nouveauTour) {
        Tour[] nouveauxTours = new Tour[tours.length + 1];
        System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);
        nouveauxTours[tours.length] = nouveauTour;
        return nouveauxTours;
    }

    @Test
    void testSequenceInitialeComplete() {
        StrategieAdaptatif strategie = new StrategieAdaptatif();

        Tour[] tours = new Tour[0];

        Decision[] sequenceAttendue = {
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR,
                Decision.TRAHIR, Decision.TRAHIR
        };

        for (int i = 0; i < sequenceAttendue.length; i++) {
            Decision decisionStrat = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decisionStrat, "La décision au tour " + (i + 1) + " devrait être " + sequenceAttendue[i]);
            Decision decisionAdversaire = Decision.COOPERER;
            Tour tour = new Tour(decisionStrat, decisionAdversaire);
            tours = ajouterTour(tours, tour);
        }
    }

    @Test
    void testSequenceInitialeAvecAdversaireQuiTrahit() {
        StrategieAdaptatif strategie = new StrategieAdaptatif();

        Tour[] tours = new Tour[0];

        Decision[] sequenceAttendue = {
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR,
                Decision.TRAHIR, Decision.TRAHIR
        };

        for (int i = 0; i < sequenceAttendue.length; i++) {
            Decision decisionStrat = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decisionStrat, "La décision au tour " + (i + 1) + " devrait être " + sequenceAttendue[i]);
            Decision decisionAdversaire = Decision.TRAHIR;
            Tour tour = new Tour(decisionStrat, decisionAdversaire);
            tours = ajouterTour(tours, tour);
        }
    }

    @Test
    void testSequenceInitialeAvecAdversaireAlternant() {
        StrategieAdaptatif strategie = new StrategieAdaptatif();

        Tour[] tours = new Tour[0];

        Decision[] sequenceAttendue = {
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR,
                Decision.TRAHIR, Decision.TRAHIR
        };

        for (int i = 0; i < sequenceAttendue.length; i++) {
            Decision decisionStrat = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decisionStrat, "La décision au tour " + (i + 1) + " devrait être " + sequenceAttendue[i]);
            Decision decisionAdversaire = (i % 2 == 0) ? Decision.COOPERER : Decision.TRAHIR;
            Tour tour = new Tour(decisionStrat, decisionAdversaire);
            tours = ajouterTour(tours, tour);
        }
    }

    @Test
    void testChoixApresSequenceCoopererMeilleur() {

        StrategieAdaptatif strategie = new StrategieAdaptatif();
        Tour[] tours = new Tour[11];

        // Tours 1 à 6 : La stratégie coopère, l'adversaire coopère
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[0], 1, 2);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0]}, 1, 2);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1]}, 1, 2);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2]}, 1, 2);
        tours[4] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3]}, 1, 2);
        tours[5] = new Tour(Decision.COOPERER, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4]}, 1, 2);

        // Tours 7 à 11 : La stratégie trahit, l'adversaire trahit
        tours[6] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5]}, 1, 2);
        tours[7] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6]}, 1, 2);
        tours[8] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7]}, 1, 2);
        tours[9] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7], tours[8]}, 1, 2);
        tours[10] = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7], tours[8], tours[9]}, 1, 2);

        Decision decision12 = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision12, "La stratégie devrait coopérer car coopérer a donné un meilleur gain moyen.");
    }

    @Test
    void testChoixApresSequenceTrahirMeilleur() {

        StrategieAdaptatif strategie = new StrategieAdaptatif();
        Tour[] tours = new Tour[11];

        // Tours 1 à 6 : La stratégie coopère, l'adversaire trahit
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[0], 1, 2);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0]}, 1, 2);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1]}, 1, 2);
        tours[3] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2]}, 1, 2);
        tours[4] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3]}, 1, 2);
        tours[5] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4]}, 1, 2);

        // Tours 7 à 11 : La stratégie trahit, l'adversaire coopère
        tours[6] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5]}, 1, 2);
        tours[7] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6]}, 1, 2);
        tours[8] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7]}, 1, 2);
        tours[9] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7], tours[8]}, 1, 2);
        tours[10] = new Tour(Decision.TRAHIR, Decision.COOPERER);
        strategie.deciderTour(new Tour[]{tours[0], tours[1], tours[2], tours[3], tours[4], tours[5], tours[6], tours[7], tours[8], tours[9]}, 1, 2);

        Decision decision12 = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision12, "La stratégie devrait trahir car trahir a donné un meilleur gain moyen.");
    }

}
