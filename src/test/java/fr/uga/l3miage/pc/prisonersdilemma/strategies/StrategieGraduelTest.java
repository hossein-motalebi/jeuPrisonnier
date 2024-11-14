package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategieGraduelTest {

    Strategie strategie;
    @BeforeEach
    public void setUp() {
        strategie= StrategieFactory.creeStrategie(TypeStrategie.GRADUEL);

    }
    @Test
    public void testCoopererAuDebut() {
        Tour[] tours = new Tour[0];
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision);
    }

    @Test
    public void testTrahirApresPremiereTrahison() {
        Tour[] tours = {new Tour(Decision.COOPERER, Decision.TRAHIR)};
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decision);
    }

    @Test
    public void testTrahirAutantDeFoisQueAdversaire() {
        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.TRAHIR),
                new Tour(Decision.TRAHIR, Decision.COOPERER)
        };
        Decision decision = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decision);

    }

    @Test
    public void testTrahirApresTrahison() {
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
        assertEquals(decision, Decision.COOPERER);

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
        assertEquals(decision, Decision.COOPERER);

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
        assertEquals(decision, Decision.COOPERER);

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
        assertEquals(decision, Decision.TRAHIR);
    }
}


