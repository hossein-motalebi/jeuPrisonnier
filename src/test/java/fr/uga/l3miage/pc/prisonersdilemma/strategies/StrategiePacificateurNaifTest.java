package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StrategiePacificateurNaifTest {

    Strategie strategie;
    Tour[] tours;
    Random mockRandom;

    @BeforeEach
    public void setUp() {
        strategie = StrategieFactory.creeStrategie(TypeStrategie.PACIFICATEUR_NAIF);
        mockRandom = mock(Random.class);

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
