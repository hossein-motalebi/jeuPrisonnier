package fr.uga.l3miage.pc.prisonersdilemma.service;

import fr.uga.l3miage.pc.prisonersdilemma.dto.*;
import fr.uga.l3miage.pc.prisonersdilemma.enums.*;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import fr.uga.l3miage.pc.prisonersdilemma.mappers.OutPartieDtoMapper;
import fr.uga.l3miage.pc.prisonersdilemma.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PartieServiceTest {

    private JoueurService joueurService;
    private PartieService partieService;

    @BeforeEach
    public void setUp() {
        joueurService = Mockito.mock(JoueurService.class);
        partieService = new PartieService(joueurService);
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

        Strategie strategie= StrategieFactory.creeStrategie(TypeStrategie.PAVLOV);
        Joueur joueur1 = new JoueurHumain("Player1", 1);
        Joueur joueur2 = new JoueurBot("Player2", strategie, 2);

        when(joueurService.creerUser("Player1", false, null,false)).thenReturn(joueur1);
        when(joueurService.creerUser("Player2", true, TypeStrategie.PAVLOV,false)).thenReturn(joueur2);

        OutPartieDTO actualOutPartieDTO = partieService.demarrerPartie(initPartieDTO);

        OutPartieDTO expectedOutPartieDTO = OutPartieDtoMapper.map(new Partie(10, joueur1, joueur2));

        assertEquals(expectedOutPartieDTO, actualOutPartieDTO);
        verify(joueurService, times(1)).creerUser("Player1", false, null,false);
        verify(joueurService, times(1)).creerUser("Player2", true, TypeStrategie.PAVLOV , false);
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

        when(joueurService.creerUser("Human1", false, null,false)).thenReturn(joueur1);
        when(joueurService.creerUser("Human2", false, null,false)).thenReturn(joueur2);
        partieService.demarrerPartie(initPartieDTO);

        // Création du DecisionDTO avec les décisions des deux joueurs
        DecisionDTO decisionDTO = new DecisionDTO(Decision.COOPERER, Decision.TRAHIR);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieService.jouerTour(decisionDTO);

        // Vérifications
        assertEquals(4, outPartieDTO.getNbTourLeft());

        // Récupérer le dernier tour joué
        Tour[] tours = partieService.getPartie().getToursCopy();
        Tour dernierTour = tours[tours.length - 1];

        assertEquals(Decision.COOPERER, dernierTour.getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur2());

        // Vérifier les scores
        // Remplacez les valeurs attendues par les scores corrects selon les règles de
        // votre jeu
        int scoreAttenduJoueur1 = partieService.getPartie().getJoueur1().getScore();
        int scoreAttenduJoueur2 = partieService.getPartie().getJoueur2().getScore();

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

        when(joueurService.creerUser("Human1", false, null,false)).thenReturn(joueur1);
        when(joueurService.creerUser("Human2", false, null,false)).thenReturn(joueur2);
        partieService.demarrerPartie(initPartieDTO);

        // Création du DecisionDTO avec une décision manquante pour le joueur 1
        DecisionDTO decisionDTO = new DecisionDTO(null, Decision.COOPERER);

        // Vérifier que l'exception est lancée
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            partieService.jouerTour(decisionDTO);
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

        Strategie strategie= StrategieFactory.creeStrategie(TypeStrategie.TOUJOURS_COOPERER);
        Joueur joueur1 = new JoueurHumain("human1", 1);
        Joueur joueur2 = new JoueurBot("Bot2", strategie, 2);

        when(joueurService.creerUser("Human1", false, null,false)).thenReturn(joueur1);
        when(joueurService.creerUser("Bot2", true, TypeStrategie.TOUJOURS_COOPERER,true)).thenReturn(joueur2);

        OutPartieDTO outPartieAttenduDto = OutPartieDtoMapper.map(new Partie(5, joueur1, joueur2));
        outPartieAttenduDto.setScoreJoueur1(5);
        outPartieAttenduDto.setScoreJoueur2(0);
        outPartieAttenduDto.setNbTourLeft(4);
        Tour tour= new Tour(Decision.TRAHIR, Decision.COOPERER);
        tour.setEstFini(true);
        outPartieAttenduDto.setHistorique(new Tour[] {tour});


        partieService.demarrerPartie(initPartieDTO);
        // Décision du bot fournie (doit être ignorée)
        DecisionDTO decisionDTO = new DecisionDTO(Decision.TRAHIR, Decision.COOPERER);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieService.jouerTour(decisionDTO);

        // Récupérer le dernier tour joué
        Tour[] tours = partieService.getPartie().getToursCopy();
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

        Strategie strategie= StrategieFactory.creeStrategie(TypeStrategie.TOUJOURS_TRAHIR);
        Joueur joueur1 = new JoueurBot("Bot1",strategie, 1);
        Joueur joueur2 = new JoueurBot("Bot2",strategie, 2);

        when(joueurService.creerUser("Bot1", true, TypeStrategie.TOUJOURS_TRAHIR,false)).thenReturn(joueur1);
        when(joueurService.creerUser("Bot2", true, TypeStrategie.TOUJOURS_TRAHIR,false)).thenReturn(joueur2);
        partieService.demarrerPartie(initPartieDTO);

        OutPartieDTO outPartieAttenduDto = OutPartieDtoMapper.map(new Partie(5, joueur1, joueur2));
        outPartieAttenduDto.setScoreJoueur1(1);
        outPartieAttenduDto.setScoreJoueur2(1);
        outPartieAttenduDto.setNbTourLeft(4);
        Tour tour= new Tour(Decision.TRAHIR, Decision.TRAHIR);
        tour.setEstFini(true);
        outPartieAttenduDto.setHistorique(new Tour[] {tour});

        // Pas de décisions fournies
        DecisionDTO decisionDTO = new DecisionDTO(null, null);

        // Jouer le tour
        OutPartieDTO outPartieDTO = partieService.jouerTour(decisionDTO);

        // Récupérer le dernier tour joué
        Tour[] tours = partieService.getPartie().getToursCopy();
        Tour dernierTour = tours[tours.length - 1];
        // Vérifier que les décisions proviennent des stratégies des bots
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, dernierTour.getDecisionJoueur2());

        assertEquals(outPartieAttenduDto, outPartieDTO);

    }
}
