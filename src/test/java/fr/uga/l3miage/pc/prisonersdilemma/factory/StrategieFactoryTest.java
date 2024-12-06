package fr.uga.l3miage.pc.prisonersdilemma.factory;

import fr.uga.strats.g8.strategie.DonnantDonnantStrategie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.*;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie ;

class StrategieFactoryTest {

    @Test
    void testCreeStrategie() {
        Strategie strategie = StrategieFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT);
        assertNotNull(strategie);
        assertInstanceOf(StrategieDonnantDonnant.class, strategie);
    }

    @Test
    void testCreeStrategieExtern() {
        fr.uga.strats.g8.strategie.Strategie strategie = StrategieFactory.creeStrategieExtern(TypeStrategie.DONNANT_DONNANT);
        assertNotNull(strategie);
        assertInstanceOf(DonnantDonnantStrategie.class, strategie);
    }

}