package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategieGraduelTest {

    Strategie strategie;
    @BeforeEach
    public void setUp() {
        strategie= StrategieInterneFactory.creeStrategie(TypeStrategie.GRADUEL);

    }
    @Test
    void testCoopererAuDebut() {
        Tour[] tours = new Tour[0];
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision);
    }

    @Test
    void testTrahirApresPremiereTrahison() {
        Tour[] tours = {new Tour(Decision.COOPERER, Decision.TRAHIR)};
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);
    }

    @Test
    void testTrahirAutantDeFoisQueAdversaire() {
        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER)
        };
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision);

    }

    @Test
    void testTrahirApresTrahison() {
        Tour[] tours = {

                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),

        };
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);

        //tours plus le dernier tour
        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR)
        };


        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);

        //tours plus le dernier tour
        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER)
        };
        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);



        //tours plus le dernier tour
        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.TRAHIR)
        };

        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER,decision);

        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };
        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER,decision);

        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };

        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision);

        tours = new Tour[]{
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER),
                new Tour(Decision.TRAHIR, Decision.TRAHIR),
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };

        decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR ,decision);
    }
}


