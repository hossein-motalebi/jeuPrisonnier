package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.JoueurHumain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;

class JoueurServiceImplTest {

    @Test
    void creerUserBotTest() {
        JoueurServiceImpl joueurServiceImpl = new JoueurServiceImpl();

        Joueur joueur1 = joueurServiceImpl.creerUser("Player1", true, TypeStrategie.PAVLOV, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurBot.class, joueur1);
    }

    @Test
    void creerUserHumainTest() {
        JoueurServiceImpl joueurServiceImpl = new JoueurServiceImpl();

        Joueur joueur1 = joueurServiceImpl.creerUser("Player1", false, null, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }

    @Test
    void creerUserHumainAvecStraTest() {
        JoueurServiceImpl joueurServiceImpl = new JoueurServiceImpl();

        Joueur joueur1 = joueurServiceImpl.creerUser("Player1", false, TypeStrategie.PAVLOV, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }

    @Test
    void transformerUserInternalStrategyTest() {
        JoueurServiceImpl joueurServiceImpl = new JoueurServiceImpl();
        JoueurHumain joueurHumain = new JoueurHumain("HumanPlayer", 1);

        JoueurBot result = joueurServiceImpl.transformerUser(joueurHumain, TypeStrategie.TOUJOURS_COOPERER, false);

        assertNotNull(result);
        assertInstanceOf(JoueurBot.class, result);
        assertEquals("HumanPlayer(Bot)", result.getNom());
        // Assuming JoueurBot has a method getStrategie() to confirm the strategy
    }

    @Test
    void transformerUserExternalStrategyTest() {
        JoueurServiceImpl joueurServiceImpl = new JoueurServiceImpl();
        JoueurHumain joueurHumain = new JoueurHumain("HumanPlayer", 2);

        JoueurBot result = joueurServiceImpl.transformerUser(joueurHumain, TypeStrategie.PAVLOV, true);

        assertNotNull(result);
        assertInstanceOf(JoueurBot.class, result);
        assertEquals("HumanPlayer(Bot)", result.getNom());
        // If your external strategy adapter indicates a special check or type:
    }

}