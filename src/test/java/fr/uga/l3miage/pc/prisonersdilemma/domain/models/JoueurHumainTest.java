package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JoueurHumainTest {

    private JoueurHumain joueurHumain;

    @BeforeEach
    void setUp() {
        joueurHumain = new JoueurHumain("TestJoueurHumain",1);
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
    void testAbandonner() {
        JoueurBot joueurBot = joueurHumain.abandonner(StrategieInterneFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT));
        assertNotNull(joueurBot);
        assertEquals("TestJoueurHumain(Bot)", joueurBot.getNom());

        //test avec un strategies qui n'existe pas, il doit retourner une erreur

    }
}