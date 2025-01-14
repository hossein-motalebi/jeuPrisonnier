package fr.uga.l3miage.pc.prisonersdilemma.domain.models;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class JoueurBotTest {



    @Test
    void testConstructor() {
        JoueurBot joueurBot = new JoueurBot("TestBot",
                StrategieInterneFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT),2);
        assertNotNull(joueurBot);
        assertEquals("TestBot", joueurBot.getNom());

    }
    @Test
    void testConstructorNullTypeStrategie() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new JoueurBot("TestBot", null,2));
        assertEquals("Strategie cannot be null", exception.getMessage());
    }



}
