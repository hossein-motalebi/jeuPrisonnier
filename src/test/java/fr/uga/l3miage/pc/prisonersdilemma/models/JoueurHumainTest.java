package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JoueurHumainTest {

    private JoueurHumain joueurHumain;

    @BeforeEach
    void setUp() {
        Tour[] tours = new Tour[0]; // Assuming Tour is a class and initializing an empty array
        joueurHumain = new JoueurHumain("TestJoueurHumain", tours);
    }

    @Test
    void testGetNom() {
        assertEquals("TestJoueurHumain", joueurHumain.getNom());
    }

    @Test
    void testGetScore() {
        assertEquals(0, joueurHumain.getScore());
    }

    @Test
    void testAugmenterScore() {
        joueurHumain.augmenterScore(10);
        assertEquals(10, joueurHumain.getScore());
    }

    @Test
    void testGetTours() {
        assertEquals(0, joueurHumain.getTours().length);
    }

    @Test
    void testAbandonner() {
        Tour[] tours = new Tour[0]; // Assuming Tour is a class and initializing an empty array
        JoueurBot joueurBot = joueurHumain.abandonner(TypeStrategie.DONNANT_DONNANT, tours);
        assertNotNull(joueurBot);
        assertEquals("TestJoueurHumain(Bot)", joueurBot.getNom());
        assertEquals(TypeStrategie.DONNANT_DONNANT, joueurBot.getTypeStrategie());
    }
}