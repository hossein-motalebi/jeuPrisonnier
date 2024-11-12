package fr.uga.l3miage.pc.prisonersdilemma.models;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;


public class JoueurBotTest {

    private Tour[] tours;

    @BeforeEach
    void setUp() {
        tours = new Tour[0];
    }

    @Test
    void testConstructor() {
        JoueurBot joueurBot = new JoueurBot("TestBot", TypeStrategie.DONNANT_DONNANT, tours);
        assertNotNull(joueurBot);
        assertEquals("TestBot", joueurBot.getNom());
        assertEquals(TypeStrategie.DONNANT_DONNANT, joueurBot.getTypeStrategie());
        assertEquals(0, joueurBot.getTours().length);
        //si on passe n'importe quoi à la place de type ça doit faire une erreur

        Exception exception=assertThrows(Exception.class, () -> new JoueurBot("TestBot", null, tours));
        assertEquals(exception.getMessage(),"TypeStrategie cannot be null" );
        Exception exception2=assertThrows(Exception.class, () -> new JoueurBot("TestBot",TypeStrategie.DONNANT_DONNANT, null));
        assertEquals(exception2.getMessage(),"Tours cannot be null" );


    }
}
