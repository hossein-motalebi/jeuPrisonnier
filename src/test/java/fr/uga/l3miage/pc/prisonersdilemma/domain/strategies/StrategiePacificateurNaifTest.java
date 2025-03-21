package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StrategiePacificateurNaifTest {

    Strategie strategie;
    Tour[] tours;
    SecureRandom mockRandom;

    @BeforeEach
    public void setUp() {
        strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.PACIFICATEUR_NAIF);
        mockRandom = mock(SecureRandom.class);

    }


    @Test
    void TestFiftyFiftyPremierCoup(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        tours=new Tour[0];
        ((StrategiePacificateurNaif)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }

    @Test
    void TestFiftyFiftyPremierCoup2(){
        when(mockRandom.nextBoolean()).thenReturn(false);
        tours=new Tour[0];
        ((StrategiePacificateurNaif)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);


    }

    @Test
    void testDernierCooperer(){

        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.COOPERER)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }

    @Test
    void testDernierTrahir(){


        ((StrategiePacificateurNaif)strategie).setRandom(mockRandom);
        when(mockRandom.nextInt(100)).thenReturn(65);

        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.TRAHIR)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }

    @Test
    void testDernierTrahirParfois(){


        ((StrategiePacificateurNaif)strategie).setRandom(mockRandom);
        when(mockRandom.nextInt(100)).thenReturn(99);

        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.TRAHIR)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }


}
