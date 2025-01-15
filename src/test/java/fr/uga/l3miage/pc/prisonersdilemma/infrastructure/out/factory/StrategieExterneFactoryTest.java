package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.factory;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.strats.g8.strategie.DonnantDonnantStrategie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StrategieExterneFactoryTest {

    @Test
    void testCreeStrategieExtern() {
        fr.uga.strats.g8.strategie.Strategie strategie = StrategieExterneFactory.creeStrategieExtern(TypeStrategie.DONNANT_DONNANT);
        assertNotNull(strategie);
        assertInstanceOf(DonnantDonnantStrategie.class, strategie);
    }
}
