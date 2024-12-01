package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.models.Joueur;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurBot;
import fr.uga.l3miage.pc.prisonersdilemma.models.JoueurHumain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;

class JoueurServiceTest {


    @Test
    void creerUserBotTest(){
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", true, TypeStrategie.PAVLOV);
        assertEquals("Player1", joueur1.getNom());
        assertEquals(TypeStrategie.PAVLOV, ((JoueurBot)joueur1).getTypeStrategie());
        assertInstanceOf(JoueurBot.class, joueur1);
    }

    @Test
    void creerUserHumainTest(){
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", false, null);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }
    @Test
    void creerUserHumainAvecStraTest(){
        JoueurService joueurService = new JoueurService();

        Joueur joueur1 = joueurService.creerUser("Player1", false, TypeStrategie.PAVLOV);
        assertEquals("Player1", joueur1.getNom());
        assertInstanceOf(JoueurHumain.class, joueur1);
    }

}
