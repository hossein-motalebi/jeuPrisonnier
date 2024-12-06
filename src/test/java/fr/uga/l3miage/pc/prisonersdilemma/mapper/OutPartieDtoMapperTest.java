package fr.uga.l3miage.pc.prisonersdilemma.mapper;

import fr.uga.l3miage.pc.prisonersdilemma.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;

class OutPartieDtoMapperTest {

    @Test
    void map_withHumanPlayers() {
        Joueur joueur1 = new JoueurHumain("azer", 1);
        Joueur joueur2 = new JoueurHumain("qsdf", 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals(1, result.getIdPlayer1());
        assertEquals(2, result.getIdPlayer2());
        assertEquals(0, result.getScoreJoueur1());
        assertEquals(0, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertFalse(result.isJoueur1bot());
        assertFalse(result.isJoueur2bot());
    }

    @Test
    void map_withBotPlayers() {

        JoueurBot joueur1 = new JoueurBot("kian", StrategieFactory.creeStrategie(TypeStrategie.ALEATOIRE), 1);
        JoueurBot joueur2 = new JoueurBot("hossein", StrategieFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT_ALEATOIRE), 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals("kian", result.getNomJoueur1());
        assertEquals("hossein", result.getNomJoueur2());
        assertEquals(0, result.getScoreJoueur1());
        assertEquals(0, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertTrue(result.isJoueur1bot());
        assertTrue(result.isJoueur2bot());
    }

    @Test
    void map_withMixedPlayers() {
        Joueur joueur1 = new JoueurHumain("ali", 1);
        JoueurBot joueur2 = new JoueurBot("majid", StrategieFactory.creeStrategie(TypeStrategie.TOUJOURS_TRAHIR), 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        assertEquals(1, result.getIdPlayer1());
        assertEquals(2, result.getIdPlayer2());
        assertEquals(0, result.getScoreJoueur1());
        assertEquals(0, result.getScoreJoueur2());
        assertEquals(5, result.getNbTourLeft());
        assertFalse(result.isJoueur1bot());
        assertTrue(result.isJoueur2bot());
    }

}