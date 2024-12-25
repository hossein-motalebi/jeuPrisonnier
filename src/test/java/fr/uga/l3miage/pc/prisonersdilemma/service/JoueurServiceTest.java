package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurHumain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;

class JoueurServiceTest {

    @Test
    void creerUserBotTest() {
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", true, TypeStrategie.PAVLOV, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurBot.class, joueur1);
    }

    @Test
    void creerUserHumainTest() {
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", false, null, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }

    @Test
    void creerUserHumainAvecStraTest() {
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", false, TypeStrategie.PAVLOV, false);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }

    @Test
    void transformerUserInternalStrategyTest() {
        JoueurService joueurService = new JoueurService();
        JoueurHumain joueurHumain = new JoueurHumain("HumanPlayer", 1);

        JoueurBot result = joueurService.transformerUser(joueurHumain, TypeStrategie.TOUJOURS_COOPERER, false);

        assertNotNull(result);
        assertInstanceOf(JoueurBot.class, result);
        assertEquals("HumanPlayer(Bot)", result.getNom());
        // Assuming JoueurBot has a method getStrategie() to confirm the strategy
    }

    @Test
    void transformerUserExternalStrategyTest() {
        JoueurService joueurService = new JoueurService();
        JoueurHumain joueurHumain = new JoueurHumain("HumanPlayer", 2);

        JoueurBot result = joueurService.transformerUser(joueurHumain, TypeStrategie.PAVLOV, true);

        assertNotNull(result);
        assertInstanceOf(JoueurBot.class, result);
        assertEquals("HumanPlayer(Bot)", result.getNom());
        // If your external strategy adapter indicates a special check or type:
    }

}