package fr.uga.l3miage.pc.prisonersdilemma.domain.models;


import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.factory.StrategieInterneFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartieTest {

    Joueur player1;
    Joueur player2;
    Partie partie;

    @BeforeEach
    void setUp() {
        player1 = new JoueurHumain("hehe",1);
        player2 = new JoueurBot("hoho", StrategieInterneFactory.creeStrategie(TypeStrategie.DONNANT_DONNANT),2);
        partie = new Partie(2, player1, player2);
    }

    @Test
    void testPartie(){
        assertThrows(Exception.class , () -> {
            new Partie(1, player1,null);
        }, "Joueur 2 is can't be null");


        assertThrows(Exception.class , () -> {
            new Partie(1, null, player2);
        }, "Joueur 1 is can't be null");
    }

    @Test
    void testNbMaxTours() {
        //entrée valide >= 1


        assertThrows(Exception.class , () -> {
            new Partie(0, player1, player2);
        }, "le nombre de tours inférieur à 1 doit lever une exception");

        assertThrows(Exception.class , () -> {
            new Partie(-1, player1, player2);
        }, "le nombre de tours inférieur à 1 doit lever une exception");

    }

    @Test
    void TestgetGagnantException() {

        assertThrows(IllegalStateException.class, () -> {
            partie.getGagnant();
        }, "le jeu n'est pas encore fini , cette méthode peu être accéder qu'à la fin");
    }

    @Test
    void TestGetGagnantJoueur1Gagne() {

        partie.initTour(Decision.COOPERER, Decision.COOPERER);
        partie.finishTour();

        partie.initTour();
        partie.finishTour(Decision.TRAHIR, Decision.COOPERER);

        //gagnant est player1
        assertEquals(1, partie.getGagnant());
        assertEquals(8,player1.getScore());
        assertEquals(3,player2.getScore() );
    }
    @Test
    void TestGetGagnantEgale() {
        //resultat egalité

        partie.initTour(Decision.COOPERER, Decision.COOPERER);
        partie.finishTour();
        partie.initTour(Decision.TRAHIR, Decision.TRAHIR);
        partie.finishTour();

        assertEquals(0, partie.getGagnant());
        assertEquals(4,player1.getScore());
        assertEquals(4,player2.getScore());

    }

    @Test
    void TestGetGagnantJoueur2Gagne() {
            //resultat egalité

        //gagnant est player2
        partie.initTour(Decision.COOPERER, Decision.COOPERER);
        partie.finishTour();

        partie.initTour();
        partie.finishTour(Decision.COOPERER, Decision.TRAHIR);


        assertEquals(2, partie.getGagnant());
        assertEquals(8,player2.getScore());
        assertEquals(3,player1.getScore());

    }

    @Test
    void initTourExceptMAx() {
        //le nombre de tours a atteint le maximum
        partie.initTour(Decision.COOPERER, Decision.COOPERER);
        partie.finishTour();
        partie.initTour(Decision.COOPERER, Decision.COOPERER);
        partie.finishTour();

        assertThrows(IllegalStateException.class, () -> {
            partie.initTour(Decision.COOPERER, Decision.COOPERER);
        }, "The number of tours has reached the maximum.");
    }


    @Test
    void initTourExceptNotFini() {
        //le dernier tour n'est pas toujours fini
        partie.initTour(Decision.COOPERER, Decision.COOPERER);

        assertThrows(IllegalStateException.class, () -> {
            partie.initTour(Decision.COOPERER, Decision.COOPERER);
        }, "si le dernier tour n'est pas fini un exception doit lever");
    }

    @Test
    void initTourExceptNbDecisions() {
        //Il faut passer 2 décisions en argument ou aucun.
        assertThrows(IllegalArgumentException.class, () -> {
            partie.initTour(Decision.COOPERER);
        }, "Il faut passer 2 décisions en argument ou aucun.");
    }

    @Test
    void verifierTours() {

        assertEquals(0, partie.getToursCopy().length);

        partie.initTour(Decision.COOPERER, Decision.TRAHIR);
        partie.finishTour();


        assertEquals(1, partie.getToursCopy().length);

        assertEquals(Decision.COOPERER, partie.getToursCopy()[0].getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, partie.getToursCopy()[0].getDecisionJoueur2());

    }

    @Test
    void getToursCopyEnCours(){
        //getTour envois une liste avec tours les cours terminés
        //si getTour est appelé avant qu'un tour ne soit terminé il doit pas contenir le tour en cours

        partie.initTour(Decision.COOPERER, Decision.TRAHIR);
        assertEquals(0, partie.getToursCopy().length);
    }

    @Test
    void getToursCopy(){
        //comme getTour envois une copy de tours on veut verifier que
        // les tours est identique à la liste de tours
        partie.initTour(Decision.COOPERER, Decision.TRAHIR);
        partie.finishTour();
        assertEquals(1, partie.getToursCopy().length);
        assertEquals(Decision.COOPERER, partie.getToursCopy()[0].getDecisionJoueur1());
        assertEquals(Decision.TRAHIR, partie.getToursCopy()[0].getDecisionJoueur2());

    }

    @Test
    void finishTourExceptDecision(){
        //finishTour est appelé sans passer les décisions des joueurs
        assertThrows(IllegalArgumentException.class, () -> {
            partie.finishTour(Decision.COOPERER);
        }, "Il faut passer 2 ou 0 décisions en argument ou aucun.");


        assertThrows(IllegalArgumentException.class, () -> {
            partie.finishTour(Decision.COOPERER , Decision.COOPERER ,Decision.TRAHIR);
        }, "Il faut passer 2 ou 0 décisions en argument ou aucun.");
    }

    @Test
    void finishTourExceptDecisionNull(){
        //finishTour est appelé sans passer les décisions des joueurs
        partie.initTour();
        assertThrows(IllegalArgumentException.class, () -> {
            partie.finishTour(null, null);
        }, "Il faut passer 2 ou 0 décisions en argument ou aucun.");
    }

    @Test
    void finishTourExceptDeciNotDef(){
        //les Decision des jours dans tour ne sont pas encore définies
        partie.initTour();
        assertThrows(IllegalStateException.class, () -> {
            partie.finishTour();
        }, "les Decision des jours dans tour ne sont pas encore définies");
    }

    @Test
    void finishTtourExceptSansInit(){
        //initTour() n'est pas encore applée
        assertThrows(IllegalStateException.class, () -> {
            partie.finishTour();
        }, "initTour() n'est pas encore applée mais excpeition n'est pas levée");
        partie.initTour();
        partie.finishTour(Decision.COOPERER, Decision.COOPERER);
        assertThrows(IllegalStateException.class, () -> {

            partie.finishTour();
        }, "initTour() n'est pas encore applée2");
    }








}
