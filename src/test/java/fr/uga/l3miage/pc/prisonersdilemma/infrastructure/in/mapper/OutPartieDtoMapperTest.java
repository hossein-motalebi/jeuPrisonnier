package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.mapper;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.*;


class OutPartieDtoMapperTest {

    @Test
    void map_withHumanPlayers() {
        Joueur joueur1 = new JoueurHumain("azer", 1);
        Joueur joueur2 = new JoueurHumain("qsdf", 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        Assertions.assertEquals(1, result.getIdPlayer1());
        Assertions.assertEquals(2, result.getIdPlayer2());
        Assertions.assertEquals(0, result.getScoreJoueur1());
        Assertions.assertEquals(0, result.getScoreJoueur2());
        Assertions.assertEquals(5, result.getNbTourLeft());
        Assertions.assertFalse(result.isJoueur1bot());
        Assertions.assertFalse(result.isJoueur2bot());
    }

    @Test
    void map_withBotPlayers() {

        JoueurBot joueur1 = new JoueurBot("kian", StrategieInterneFactory.creeStrategie(TypeStrategie.ALEATOIRE), 1);
        JoueurBot joueur2 = new JoueurBot("hossein", StrategieInterneFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT_ALEATOIRE), 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        Assertions.assertEquals("kian", result.getNomJoueur1());
        Assertions.assertEquals("hossein", result.getNomJoueur2());
        Assertions.assertEquals(0, result.getScoreJoueur1());
        Assertions.assertEquals(0, result.getScoreJoueur2());
        Assertions.assertEquals(5, result.getNbTourLeft());
        Assertions.assertTrue(result.isJoueur1bot());
        Assertions.assertTrue(result.isJoueur2bot());
    }

    @Test
    void map_withMixedPlayers() {
        Joueur joueur1 = new JoueurHumain("ali", 1);
        JoueurBot joueur2 = new JoueurBot("majid", StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_TRAHIR), 2);
        Partie partie = new Partie(5, joueur1, joueur2);

        OutPartieDTO result = OutPartieDtoMapper.map(partie);

        Assertions.assertEquals(1, result.getIdPlayer1());
        Assertions.assertEquals(2, result.getIdPlayer2());
        Assertions.assertEquals(0, result.getScoreJoueur1());
        Assertions.assertEquals(0, result.getScoreJoueur2());
        Assertions.assertEquals(5, result.getNbTourLeft());
        Assertions.assertFalse(result.isJoueur1bot());
        Assertions.assertTrue(result.isJoueur2bot());
    }

}