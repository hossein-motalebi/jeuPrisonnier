package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategieToujoursCoopererTest {

    @Test
    void testToujoursCoopererSansHistorique() {
        Strategie strategie = new StrategieToujoursCooperer();
        Tour[] historique = new Tour[0];

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueCooperation() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] historique = new Tour[3];
        historique[0] = new Tour(Decision.COOPERER, Decision.COOPERER);
        historique[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        historique[2] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, même si l'adversaire a toujours coopéré.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueTrahison() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] historique = new Tour[3];
        historique[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        historique[1] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        historique[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, même si l'adversaire a toujours trahi.");
    }

    @Test
    void testToujoursCoopererAvecHistoriqueMixte() {
        Strategie strategie = new StrategieToujoursCooperer();

        Tour[] historique = new Tour[4];
        historique[0] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        historique[1] = new Tour(Decision.COOPERER, Decision.COOPERER);
        historique[2] = new Tour(Decision.COOPERER, Decision.TRAHIR);
        historique[3] = new Tour(Decision.COOPERER, Decision.COOPERER);

        Decision decision = strategie.deciderTour(historique);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait toujours coopérer, quel que soit l'historique.");
    }

}
