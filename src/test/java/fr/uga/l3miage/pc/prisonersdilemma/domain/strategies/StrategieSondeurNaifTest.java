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


class StrategieSondeurNaifTest {

    Strategie strategie;
    Tour[] tours;
    SecureRandom mockRandom;
    @BeforeEach
    void setUp() {
        mockRandom = mock(SecureRandom.class);
        strategie=new StrategieSondeurNaif();
        
    }
    @Test
    void testPremierCoupTrue(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        ((StrategieSondeurNaif)strategie).setRandom(mockRandom);
        tours = new Tour[0];
        Decision decision=strategie.deciderTour(tours,1, 2);
        assertEquals(Decision.COOPERER,decision);
    }
    @Test
    void testPremierCoupFalse(){
        when(mockRandom.nextBoolean()).thenReturn(false);
        ((StrategieSondeurNaif)strategie).setRandom(mockRandom);
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
        ((StrategieSondeurNaif)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }

    @Test
    void testDernierCoupParfoisFalse(){
        tours = new Tour[]{new Tour(Decision.COOPERER,Decision.COOPERER)};
        when(mockRandom.nextInt(100)).thenReturn(10);
        ((StrategieSondeurNaif)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }

}
