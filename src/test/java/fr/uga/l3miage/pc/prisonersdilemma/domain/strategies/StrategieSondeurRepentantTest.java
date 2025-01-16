package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StrategieSondeurRepentantTest {

    Strategie strategie;
    Tour[] tours;
    SecureRandom mockRandom;
    @BeforeEach
    void setUp() {
        mockRandom = mock(SecureRandom.class);
        strategie=new StrategieSondeurRepentant();
        
    }
    @Test
    void testPremierCoupTrue(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        tours = new Tour[0];
        Decision decision=strategie.deciderTour(tours,1, 2);
        assertEquals(Decision.COOPERER,decision);
    }
    @Test
    void testPremierCoupFalse(){
        when(mockRandom.nextBoolean()).thenReturn(false);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        tours = new Tour[0];
        Decision decision=strategie.deciderTour(tours,1, 2);
        assertEquals(Decision.TRAHIR,decision);
    }

    @Test
    void testDernierCoupTrahir(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.TRAHIR)};
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR, decision);
    }


    @Test
    void testDernierCoupParfoisTrue(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER)};
        when(mockRandom.nextInt(100)).thenReturn(90);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }

    @Test
    void testDernierCoupParfoisFalse(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER)};
        when(mockRandom.nextInt(100)).thenReturn(10);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }

    @Test
    void testDernierCoupParfoisTrueOpTrahit(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER)};
        when(mockRandom.nextInt(100)).thenReturn(90);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        strategie.deciderTour(tours,1,2);

        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER),
                            new Tour(Decision.TRAHIR ,Decision.TRAHIR)};
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }
    @Test
    void testDernierCoupParfoisTrueOpCooper(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER)};
        when(mockRandom.nextInt(100)).thenReturn(90);
        ((StrategieSondeurRepentant)strategie).setRandom(mockRandom);
        strategie.deciderTour(tours,1,2);

        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER),
                new Tour(Decision.TRAHIR ,Decision.COOPERER)};
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }
}
