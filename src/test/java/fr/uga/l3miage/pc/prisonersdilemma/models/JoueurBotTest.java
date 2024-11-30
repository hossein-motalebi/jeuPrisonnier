package fr.uga.l3miage.pc.prisonersdilemma.models;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class JoueurBotTest {



    @Test
    void testConstructor() {
        JoueurBot joueurBot = new JoueurBot("TestBot", TypeStrategie.DONNANT_DONNANT,2);
        assertNotNull(joueurBot);
        assertEquals("TestBot", joueurBot.getNom());
        assertEquals(TypeStrategie.DONNANT_DONNANT, joueurBot.getTypeStrategie());

    }
    @Test
    void testConstructorNullTypeStrategie() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new JoueurBot("TestBot", null,2));
        assertEquals("TypeStrategie cannot be null", exception.getMessage());
    }



}
