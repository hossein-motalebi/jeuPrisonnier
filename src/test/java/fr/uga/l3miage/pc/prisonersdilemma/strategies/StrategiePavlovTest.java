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


class StrategiePavlovTest {

    Strategie strategie;
    Tour[] tours;
    Random mockRandom;

    @BeforeEach
    public void setUp() {
        strategie = StrategieFactory.creeStrategie(TypeStrategie.PAVLOV);
        mockRandom = mock(Random.class);

    }

    @Test
    void TestAvantDernierCinqPointTrue(){

        ((StrategiePavlov)strategie).setRandom(mockRandom);
        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.COOPERER)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }
    @Test
    void TestAvantDernierTroisPointTrue(){

        ((StrategiePavlov)strategie).setRandom(mockRandom);
        tours=new Tour[]{
                new Tour(Decision.COOPERER,Decision.COOPERER)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }
    @Test
    void TestFiftyFiftyPremierCoup(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        tours=new Tour[0];
        ((StrategiePavlov)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }

    @Test
    void TestFiftyFiftyPremierCoup2(){
        when(mockRandom.nextBoolean()).thenReturn(false);
        tours=new Tour[0];
        ((StrategiePavlov)strategie).setRandom(mockRandom);
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }

    @Test
    void TestfiftyFiftyFinFalse(){
        when(mockRandom.nextBoolean()).thenReturn(false);

        ((StrategiePavlov)strategie).setRandom(mockRandom);

        tours=new Tour[]{
                new Tour(Decision.COOPERER,Decision.TRAHIR)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.TRAHIR,decision);

    }

    @Test
    void TestfiftyFiftyFinTrue(){
        when(mockRandom.nextBoolean()).thenReturn(true);

        ((StrategiePavlov)strategie).setRandom(mockRandom);

        tours=new Tour[]{
                new Tour(Decision.COOPERER,Decision.TRAHIR)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assertEquals(Decision.COOPERER,decision);

    }



}
