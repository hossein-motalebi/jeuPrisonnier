package fr.uga.l3miage.pc.prisonersdilemma.application.service;

import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.AbandonnerDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.DecisionDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.InitPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.dto.OutPartieDTO;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import fr.uga.l3miage.pc.prisonersdilemma.infrastructure.in.web.mappers.OutPartieDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PartieServiceImplTest {

    private JoueurServiceImpl joueurServiceImpl;
    private PartieServiceImpl partieServiceImpl;

    @BeforeEach
    public void setUp() {
        joueurServiceImpl = Mockito.mock(JoueurServiceImpl.class);
        partieServiceImpl = new PartieServiceImpl(joueurServiceImpl);
    }

    @Test
    void testDemarrerPartie() {
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Player1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Player2");
        initPartieDTO.setJoueur2bot(true);
        initPartieDTO.setStrategieJoueur2(TypeStrategie.PAVLOV);
        initPartieDTO.setNbMaxTours(10);

        Strategie strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.PAVLOV);
        Joueur joueur1 = new JoueurHumain("Player1", 1);
        Joueur joueur2 = new JoueurBot("Player2", strategie, 2);

        when(joueurServiceImpl.creerUser("Player1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Player2", true, TypeStrategie.PAVLOV, false)).thenReturn(joueur2);

        OutPartieDTO actualOutPartieDTO = partieServiceImpl.demarrerPartie(initPartieDTO);

        OutPartieDTO expectedOutPartieDTO = OutPartieDtoMapper.map(new Partie(10, joueur1, joueur2));

        assertEquals(expectedOutPartieDTO, actualOutPartieDTO);
        verify(joueurServiceImpl, times(1)).creerUser("Player1", false, null, false);
        verify(joueurServiceImpl, times(1)).creerUser("Player2", true, TypeStrategie.PAVLOV, false);
    }

    @Test
    void testJouerTour_BothHumansProvideDecisions() {
        // Initialisation de la partie avec deux joueurs humains
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Human1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Human2");
        initPartieDTO.setJoueur2bot(false);
        initPartieDTO.setNbMaxTours(5);

        Joueur joueur1 = new JoueurHumain("human1", 1);
        Joueur joueur2 = new JoueurHumain("human2", 2);

        when(joueurServiceImpl.creerUser("Human1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Human2", false, null, false)).thenReturn(joueur2);
        partieServiceImpl.demarrerPartie(initPartieDTO);

        // Création du DecisionDTO avec les décisions des deux joueurs
        DecisionDTO decisionDTO = new DecisionDTO(Decision.COOPERER, Decision.TRAHIR);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO);

        // Vérifications
        assertEquals(4, outPartieDTO.getNbTourLeft());

        // Récupérer le dernier tour joué
        Tour[] tours = partieServiceImpl.getPartie().getToursCopy();
        Tour dernierTour = tours[tours.length - 1];

        assertEquals(Decision.COOPERER, dernierTour.getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur2());

        // Vérifier les scores
        // Remplacez les valeurs attendues par les scores corrects selon les règles de
        // votre jeu
        int scoreAttenduJoueur1 = partieServiceImpl.getPartie().getJoueur1().getScore();
        int scoreAttenduJoueur2 = partieServiceImpl.getPartie().getJoueur2().getScore();

        assertEquals(scoreAttenduJoueur1, outPartieDTO.getScoreJoueur1());
        assertEquals(scoreAttenduJoueur2, outPartieDTO.getScoreJoueur2());
    }

    @Test
    void testJouerTour_HumanMissingDecision_ShouldThrowException() {
        // Initialisation de la partie avec deux joueurs humains
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Human1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Human2");
        initPartieDTO.setJoueur2bot(false);
        initPartieDTO.setNbMaxTours(5);
        Joueur joueur1 = new JoueurHumain("human1", 1);
        Joueur joueur2 = new JoueurHumain("human2", 2);

        when(joueurServiceImpl.creerUser("Human1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Human2", false, null, false)).thenReturn(joueur2);
        partieServiceImpl.demarrerPartie(initPartieDTO);

        // Création du DecisionDTO avec une décision manquante pour le joueur 1
        DecisionDTO decisionDTO = new DecisionDTO(null, Decision.COOPERER);

        // Vérifier que l'exception est lancée
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partieServiceImpl.jouerTour(decisionDTO);
        });

        assertEquals("Le joueur 1 n'a pas fait de choix", exception.getMessage());
    }

    @Test
    void testJouerTour_BotDecisionProvided_ShouldUseStrategy() {
        // Initialisation de la partie avec un humain et un bot
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Human1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Bot2");
        initPartieDTO.setJoueur2bot(true);
        initPartieDTO.setStrategieJoueur2(TypeStrategie.TOUJOURS_COOPERER);
        initPartieDTO.setNbMaxTours(5);
        initPartieDTO.setStrategieExterneJoueur2(true);

        Strategie strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER);
        Joueur joueur1 = new JoueurHumain("human1", 1);
        Joueur joueur2 = new JoueurBot("Bot2", strategie, 2);

        when(joueurServiceImpl.creerUser("Human1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Bot2", true, TypeStrategie.TOUJOURS_COOPERER, true)).thenReturn(joueur2);

        OutPartieDTO outPartieAttenduDto = OutPartieDtoMapper.map(new Partie(5, joueur1, joueur2));
        outPartieAttenduDto.setScoreJoueur1(5);
        outPartieAttenduDto.setScoreJoueur2(0);
        outPartieAttenduDto.setNbTourLeft(4);
        Tour tour = new Tour(Decision.TRAHIR, Decision.COOPERER);
        tour.setEstFini(true);
        outPartieAttenduDto.setHistorique(new Tour[] { tour });

        partieServiceImpl.demarrerPartie(initPartieDTO);
        // Décision du bot fournie (doit être ignorée)
        DecisionDTO decisionDTO = new DecisionDTO(Decision.TRAHIR, Decision.COOPERER);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO);

        // Récupérer le dernier tour joué
        Tour[] tours = partieServiceImpl.getPartie().getToursCopy();
        Tour dernierTour = tours[tours.length - 1];
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur1());
        // La décision du bot doit provenir de sa stratégie, pas de la décision fo
        // rnie
        assertEquals(Decision.COOPERER, dernierTour.getDecisionJoueur2());
        assertEquals(outPartieAttenduDto, outPartieDTO);
    }

    @Test
    void testJouerTour_BothBots() {
        // Initialisation de la partie avec deux bots
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Bot1");
        initPartieDTO.setJoueur1bot(true);
        initPartieDTO.setStrategieJoueur1(TypeStrategie.TOUJOURS_TRAHIR);
        initPartieDTO.setNomJoueur2("Bot2");
        initPartieDTO.setJoueur2bot(true);
        initPartieDTO.setStrategieJoueur2(TypeStrategie.TOUJOURS_TRAHIR);
        initPartieDTO.setNbMaxTours(5);

        Strategie strategie = StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_TRAHIR);
        Joueur joueur1 = new JoueurBot("Bot1", strategie, 1);
        Joueur joueur2 = new JoueurBot("Bot2", strategie, 2);

        when(joueurServiceImpl.creerUser("Bot1", true, TypeStrategie.TOUJOURS_TRAHIR, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Bot2", true, TypeStrategie.TOUJOURS_TRAHIR, false)).thenReturn(joueur2);
        partieServiceImpl.demarrerPartie(initPartieDTO);

        OutPartieDTO outPartieAttenduDto = OutPartieDtoMapper.map(new Partie(5, joueur1, joueur2));
        outPartieAttenduDto.setScoreJoueur1(1);
        outPartieAttenduDto.setScoreJoueur2(1);
        outPartieAttenduDto.setNbTourLeft(4);
        Tour tour = new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tour.setEstFini(true);
        outPartieAttenduDto.setHistorique(new Tour[] { tour });

        // Pas de décisions fournies
        DecisionDTO decisionDTO = new DecisionDTO(null, null);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieServiceImpl.jouerTour(decisionDTO);

        // Récupérer le dernier tour joué
        Tour[] tours = partieServiceImpl.getPartie().getToursCopy();
        Tour dernierTour = tours[tours.length - 1];
        // Vérifier que les décisions proviennent des stratégies des bots
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur2());

        assertEquals(outPartieAttenduDto, outPartieDTO);

    }

    @Test
    void testAbandonner_Player1BecomesBot() {
        // given
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Human1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Human2");
        initPartieDTO.setJoueur2bot(false);
        initPartieDTO.setNbMaxTours(3);

        Joueur joueur1 = new JoueurHumain("Human1", 1);
        Joueur joueur2 = new JoueurHumain("Human2", 2);

        when(joueurServiceImpl.creerUser("Human1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Human2", false, null, false)).thenReturn(joueur2);

        partieServiceImpl.demarrerPartie(initPartieDTO);

        // when
        AbandonnerDTO abandonnerDTO = new AbandonnerDTO(1, TypeStrategie.TOUJOURS_COOPERER, false);
        JoueurBot mockedBot = new JoueurBot("Human1", StrategieInterneFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER),
                1);
        when(joueurServiceImpl.transformerUser((JoueurHumain) joueur1, TypeStrategie.TOUJOURS_COOPERER, false))
                .thenReturn(mockedBot);

        OutPartieDTO outPartieDTO = partieServiceImpl.abandonner(abandonnerDTO);

        // then
        verify(joueurServiceImpl, times(1))
                .transformerUser((JoueurHumain) joueur1, TypeStrategie.TOUJOURS_COOPERER, false);

        assertTrue(outPartieDTO.isJoueur1bot());
        assertFalse(outPartieDTO.isJoueur2bot());

        assertEquals("Human1", outPartieDTO.getNomJoueur1());
        assertEquals(mockedBot.getScore(), outPartieDTO.getScoreJoueur1());
    }

    @Test
    void testAbandonner_Player2BecomesBot() {
        // given
        InitPartieDTO initPartieDTO = new InitPartieDTO();
        initPartieDTO.setNomJoueur1("Human1");
        initPartieDTO.setJoueur1bot(false);
        initPartieDTO.setNomJoueur2("Human2");
        initPartieDTO.setJoueur2bot(false);
        initPartieDTO.setNbMaxTours(5);

        Joueur joueur1 = new JoueurHumain("Human1", 1);
        Joueur joueur2 = new JoueurHumain("Human2", 2);

        when(joueurServiceImpl.creerUser("Human1", false, null, false)).thenReturn(joueur1);
        when(joueurServiceImpl.creerUser("Human2", false, null, false)).thenReturn(joueur2);

        partieServiceImpl.demarrerPartie(initPartieDTO);

        // when
        AbandonnerDTO abandonnerDTO = new AbandonnerDTO(2, TypeStrategie.PAVLOV, true);
        JoueurBot mockedBot = new JoueurBot("Human2", StrategieInterneFactory.creeStrategie(TypeStrategie.PAVLOV), 2);
        when(joueurServiceImpl.transformerUser((JoueurHumain) joueur2, TypeStrategie.PAVLOV, true)).thenReturn(mockedBot);

        OutPartieDTO outPartieDTO = partieServiceImpl.abandonner(abandonnerDTO);

        // then
        verify(joueurServiceImpl, times(1))
                .transformerUser((JoueurHumain) joueur2, TypeStrategie.PAVLOV, true);

        assertTrue(outPartieDTO.isJoueur2bot());
        assertFalse(outPartieDTO.isJoueur1bot());
        assertEquals("Human2", outPartieDTO.getNomJoueur2());
        assertEquals(mockedBot.getScore(), outPartieDTO.getScoreJoueur2());
    }

}
