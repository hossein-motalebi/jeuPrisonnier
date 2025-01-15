package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.mappers.OutPartieDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PartieServiceImplTest {

    private JoueurServiceImpl joueurServiceImpl;
    private PartieServiceImpl partieServiceImpl;
    private SseService sseService;

    @BeforeEach
    public void setUp() {
        joueurServiceImpl = Mockito.mock(JoueurServiceImpl.class);
        Joueur joueur1 = new JoueurHumain("Player1", 1);
        Joueur joueur2 = new JoueurHumain("En attend", 2);
        when(joueurServiceImpl.creerUser("Player1", 1)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("En attend", 2)).thenReturn(joueur2);
        sseService = Mockito.mock(SseService.class);
        partieServiceImpl = new PartieServiceImpl(joueurServiceImpl, sseService);
        InitPartieInDTO initPartieInDTO = new InitPartieInDTO();
        initPartieInDTO.setNomJoueur("Player1");
        initPartieInDTO.setNbMaxTours(5);
        partieServiceImpl.initPartie(initPartieInDTO);
    }

    @Test

    void testInitPartie() {
        InitPartieInDTO initPartieInDTO = new InitPartieInDTO();
        initPartieInDTO.setNomJoueur("Player1");
        initPartieInDTO.setNbMaxTours(10);

        Joueur joueur1 = new JoueurHumain("Player1", 1);
        Joueur joueur2 = new JoueurHumain("En attend", 2);

        when(joueurServiceImpl.creerUser("Player1", 1)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("En attend", 2)).thenReturn(joueur2);

        int idPartie = partieServiceImpl.initPartie(initPartieInDTO);

        assertNotNull(partieServiceImpl.getParties().get(idPartie));
        assertEquals(10001, idPartie);
    }

    @Test
    void testJoinPartie() {
        Partie partie = new Partie(10, new JoueurHumain("Player1", 1), new JoueurHumain("En attend", 2));

        partieServiceImpl.getParties().put(10000, partie);

        JoinPartieRequestDTO joinPartieRequestDTO = new JoinPartieRequestDTO("Player2");

        partieServiceImpl.joinPartie(10000, joinPartieRequestDTO);

        assertEquals("Player2", partie.getJoueur2().getNom());
        assertEquals(EtatPartie.ENCOURS, partie.getEtatPartie());
        verify(sseService, times(1)).sendEvent(eq(10000), any(OutPartieDTO.class));
    }

    @Test
    void testJouerTour_BothHumansProvideDecisions() {
        // Initialisation de la partie avec deux joueurs humains

        int idPartie = 10000;

        // Création du DecisionDTO avec les décisions des deux joueurs
        DecisionDTO decisionDTO = new DecisionDTO(Decision.COOPERER, Decision.TRAHIR);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO, idPartie);

        // Vérifications
        assertEquals(4, outPartieDTO.getNbTourLeft());

        // Récupérer le dernier tour joué
        Tour[] tours = partieServiceImpl.getParties().get(idPartie).getToursCopy();
        Tour dernierTour = tours[tours.length - 1];

        assertEquals(Decision.COOPERER, dernierTour.getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur2());

        // Vérifier les scores
        // Remplacez les valeurs attendues par les scores corrects selon les règles de
        // votre jeu
        int scoreAttenduJoueur1 = partieServiceImpl.getParties().get(idPartie).getJoueur1().getScore();
        int scoreAttenduJoueur2 = partieServiceImpl.getParties().get(idPartie).getJoueur2().getScore();

        assertEquals(scoreAttenduJoueur1, outPartieDTO.getScoreJoueur1());
        assertEquals(scoreAttenduJoueur2, outPartieDTO.getScoreJoueur2());
    }

    @Test
    void testJouerTour_HumanMissingDecision_ShouldThrowException() {
        // Initialisation de la partie avec deux joueurs humains

        // Création du DecisionDTO avec une décision manquante pour le joueur 1
        DecisionDTO decisionDTO = new DecisionDTO(null, Decision.COOPERER);

        // Vérifier que l'exception est lancée
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partieServiceImpl.jouerTour(decisionDTO, 10000);
        });

        assertEquals("Le joueur 1 n'a pas fait de choix", exception.getMessage());
    }

    @Test
    void testJouerTour_BotDecisionProvided_ShouldUseStrategy() {
        // Initialisation de la partie avec un humain et un bot

        Strategie strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER);
        Partie partie = partieServiceImpl.getParties().get(10000);
        ((JoueurHumain) partie.getJoueur2()).abandonner(strategie);

        OutPartieDTO outPartieAttenduDto = OutPartieDtoMapper.map(partie);
        outPartieAttenduDto.setScoreJoueur1(5);
        outPartieAttenduDto.setScoreJoueur2(0);
        outPartieAttenduDto.setNbTourLeft(4);
        Tour tour = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tour.setEstFini(true);
        outPartieAttenduDto.setHistorique(new Tour[] { tour });

        // Décision du bot fournie (doit être ignorée)
        DecisionDTO decisionDTO = new DecisionDTO(Decision.TRAHIR, Decision.COOPERER);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO, 10000);

        // Récupérer le dernier tour joué
        Tour[] tours = partie.getToursCopy();
        Tour dernierTour = tours[tours.length - 1];
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur1());
        // La décision du bot doit provenir de sa stratégie, pas de la décision fo
        // rnie
        assertEquals(Decision.COOPERER, dernierTour.getDecisionJoueur2());
        assertEquals(outPartieAttenduDto, outPartieDTO);
    }

    @Test
    void testAbandonner_Player1BecomesBot() {
        // given

        Partie partie = partieServiceImpl.getParties().get(10000);

        // when
        Joueur joueur1 = partie.getJoueur1();
        AbandonnerDTO abandonnerDTO = new AbandonnerDTO(1, TypeStrategie.TOUJOURS_COOPERER, false);
        JoueurBot mockedBot = new JoueurBot("Human1",
                StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER),
                1);
        when(joueurServiceImpl.transformerUser((JoueurHumain) partie.getJoueur1(), TypeStrategie.TOUJOURS_COOPERER,
                false))
                .thenReturn(mockedBot);

        partieServiceImpl.abandonner(10000, abandonnerDTO);

        OutPartieDTO outPartieDTO = OutPartieDtoMapper.map(partie);

        // then

        verify(joueurServiceImpl, times(1))
                .transformerUser((JoueurHumain) joueur1, TypeStrategie.TOUJOURS_COOPERER, false);

        Assertions.assertTrue(outPartieDTO.isJoueur1bot());
        Assertions.assertFalse(outPartieDTO.isJoueur2bot());

        assertEquals("Human1", outPartieDTO.getNomJoueur1());
        assertEquals(mockedBot.getScore(), outPartieDTO.getScoreJoueur1());
    }

    @Test
    void testAbandonner_Player2BecomesBot() {
        // given

        Partie partie = partieServiceImpl.getParties().get(10000);
        Joueur joueur2 = partie.getJoueur2();
        // when
        AbandonnerDTO abandonnerDTO = new AbandonnerDTO(2, TypeStrategie.PAVLOV, false);
        JoueurBot mockedBot = new JoueurBot("En attend(bot)",
                StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER),
                1);
        when(joueurServiceImpl.transformerUser((JoueurHumain) partie.getJoueur2(), TypeStrategie.PAVLOV, false))
                .thenReturn(mockedBot);

        partieServiceImpl.abandonner(10000, abandonnerDTO);

        OutPartieDTO outPartieDTO = OutPartieDtoMapper.map(partie);

        // then
        verify(joueurServiceImpl, times(1))
                .transformerUser((JoueurHumain) joueur2, TypeStrategie.PAVLOV, false);

        assertTrue(outPartieDTO.isJoueur2bot());
        assertFalse(outPartieDTO.isJoueur1bot());

        assertEquals("En attend(bot)", outPartieDTO.getNomJoueur2());
        assertEquals(mockedBot.getScore(), outPartieDTO.getScoreJoueur2());
    }

}
