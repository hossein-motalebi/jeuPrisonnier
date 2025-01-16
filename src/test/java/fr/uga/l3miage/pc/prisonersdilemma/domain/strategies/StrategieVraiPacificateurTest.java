package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StrategieVraiPacificateurTest {

    Strategie strategie;

    public StrategieVraiPacificateurTest(){
        strategie= new StrategieVraiPacificateur();
    }

    @Test
    void testPremierCoup(){
        Tour[] tours = new Tour[0];
        Decision decision= strategie.deciderTour(tours, 1 ,2);
        assertEquals(Decision.COOPERER,decision);
    }

    @Test
    void  testAdversaireCooper(){
        Tour[] tours = new Tour[]{
                new Tour(Decision.TRAHIR,Decision.COOPERER)
        };
        Decision decision= strategie.deciderTour(tours, 1 ,2);
        assertEquals(Decision.COOPERER,decision);
    }

    @Test
    void testTrahirNonConsecutive(){
        Tour[] tours = new Tour[]{
                new Tour(Decision.TRAHIR,Decision.TRAHIR)
        };
        Decision decision= strategie.deciderTour(tours, 1 ,2);
        assertEquals(Decision.COOPERER,decision);
    }

    @Test
    void testTrahirConsecutiveSansPaix(){
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextInt(100)).thenReturn(60);
        ((StrategieVraiPacificateur) strategie).setRandom(mockRandom);

        Tour[] tours = new Tour[]{
                new Tour(Decision.TRAHIR,Decision.TRAHIR),
                new Tour(Decision.COOPERER,Decision.TRAHIR)
        };
        strategie.deciderTour(tours, 1 ,2);
        Decision decision= strategie.deciderTour(tours, 1 ,2);
        assertEquals(Decision.TRAHIR,decision);
    }

    @Test
    void testTrahirConsecutiveAvecPaix(){
        SecureRandom mockRandom = mock(SecureRandom.class);
        when(mockRandom.nextInt(100)).thenReturn(90);
        ((StrategieVraiPacificateur) strategie).setRandom(mockRandom);

        Tour[] tours = new Tour[]{
                new Tour(Decision.TRAHIR,Decision.TRAHIR),
                new Tour(Decision.COOPERER,Decision.TRAHIR)
        };
        strategie.deciderTour(tours, 1 ,2);
        Decision decision= strategie.deciderTour(tours, 1 ,2);
        assertEquals(Decision.COOPERER,decision);
    }


}
