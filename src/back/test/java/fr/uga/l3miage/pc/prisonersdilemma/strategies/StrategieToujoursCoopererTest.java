package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategieToujoursCoopererTest {

    @Test
    void testToujoursCoopererSansHistorique() {
        Strategie strategie = new StrategieToujoursCooperer();
        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueCooperation() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, même si l'adversaire a toujours coopéré.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueTrahison() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] tours = new Tour[3];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, même si l'adversaire a toujours trahi.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueMixte() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] tours = new Tour[4];
        tours[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        tours[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        tours[3] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(tours,1,2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, quel que soit l'historique.");
    }

}
