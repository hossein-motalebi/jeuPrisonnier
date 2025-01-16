package fr.uga.l3miage.pc.prisonersdilemma.domain.factory;

import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.StrategieDonnantDonnant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;

class StrategieInterneFactoryTest {

    @Test
    void testCreeStrategie() {
        Strategie strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT);
        assertNotNull(strategie);
        assertInstanceOf(StrategieDonnantDonnant.class, strategie);
    }



}