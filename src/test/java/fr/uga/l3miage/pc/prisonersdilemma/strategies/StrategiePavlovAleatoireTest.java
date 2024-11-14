package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StrategiePavlovAleatoireTest {
    
    Strategie strategie;
    Tour[] tours;
    Random mockRandom;
    
    @BeforeEach
    public void setUp() {
        strategie = StrategieFactory.creeStrategie(TypeStrategie.PAVLOV_ALEATOIRE);
        mockRandom = mock(Random.class);
 
    }        
    
    @Test
    void AvantDernierCinqOuTroisPointParfoisTrue(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(100)).thenReturn(85);

        ((StrategiePavlovAleatoire)strategie).setRandom(mockRandom);

        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.COOPERER)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assert(decision==Decision.COOPERER);

    }
    @Test
    void AvantDernierCinqOuTroisPointParfoisFalse(){
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(100)).thenReturn(65);

        ((StrategiePavlovAleatoire)strategie).setRandom(mockRandom);

        tours=new Tour[]{
                new Tour(Decision.TRAHIR,Decision.COOPERER)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assert(decision==Decision.TRAHIR);

    }
    @Test
    void fiftyFifty(){
        when(mockRandom.nextBoolean()).thenReturn(false);
        when(mockRandom.nextInt(100)).thenReturn(65);

        ((StrategiePavlovAleatoire)strategie).setRandom(mockRandom);

        tours=new Tour[]{
                new Tour(Decision.COOPERER,Decision.TRAHIR)
        };
        Decision decision=strategie.deciderTour(tours,1,2);
        assert(decision==Decision.TRAHIR);

    }
    
    

}
