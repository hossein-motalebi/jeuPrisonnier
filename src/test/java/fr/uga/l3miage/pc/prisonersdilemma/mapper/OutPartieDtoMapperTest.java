package fr.uga.l3miage.pc.prisonersdilemma.mapper;

import fr.uga.l3miage.pc.prisonersdilemma.DTO.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;


class OutPartieDtoMapperTest {

    @Test
    void map_withHumanPlayers() {
        Joueur joueur1 = new JoueurHumain("1", 1);
        Joueur joueur2 = new JoueurHumain("2", 2);
        Partie partie = new Partie(5,joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals(1, result.getIdPlayer1());
        assertEquals(2, result.getIdPlayer2());
        assertEquals(0, result.getScoreJoueur1());
        assertEquals(0, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertFalse(result.isJoueur1bot());
        assertFalse(result.isJoueur2bot());
    }
    /*
    @Test
    void map_withBotPlayers() {
        JoueurBot joueur1 = new JoueurBot("1", 10, TypeStrategie.ALEATOIRE);
        JoueurBot joueur2 = new JoueurBot("2", 20, TypeStrategie.COOPERATIF);
        Partie partie = new Partie(joueur1, joueur2, 5, new Tour[0]);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals("1", result.getIdPlayer1());
        assertEquals("2", result.getIdPlayer2());
        assertEquals(10, result.getScoreJoueur1());
        assertEquals(20, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertTrue(result.isJoueur1bot());
        assertTrue(result.isJoueur2bot());
        assertEquals(TypeStrategie.ALEATOIRE, result.getStrategieJoueur1());
        assertEquals(TypeStrategie.COOPERATIF, result.getStrategieJoueur2());
    }

    @Test
    void map_withMixedPlayers() {
        Joueur joueur1 = new Joueur("1", 10);
        JoueurBot joueur2 = new JoueurBot("2", 20, TypeStrategie.TRAITRE);
        Partie partie = new Partie(joueur1, joueur2, 5, new Tour[0]);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals("1", result.getIdPlayer1());
        assertEquals("2", result.getIdPlayer2());
        assertEquals(10, result.getScoreJoueur1());
        assertEquals(20, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertFalse(result.isJoueur1bot());
        assertTrue(result.isJoueur2bot());
        assertEquals(TypeStrategie.TRAITRE, result.getStrategieJoueur2());
    }

    @Test
    void map_withNoToursLeft() {
        Joueur joueur1 = new Joueur("1", 10);
        Joueur joueur2 = new Joueur("2", 20);
        Partie partie = new Partie(joueur1, joueur2, 5, new Tour[5]);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals(0, result.getNbTourLeft());
    }
    */

}