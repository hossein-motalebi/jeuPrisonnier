package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.ResultatTour;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class TourTest {

    private Tour tour;

    @BeforeEach
    void setUp() {
        tour = new Tour();
    }

    @Test
    void testConstructeurPartielleNull (){

        assertThrows(Exception.class , () ->{
            tour=new Tour(Decision.COOPERER , null);
        });
        assertThrows(Exception.class , () ->{
            tour=new Tour(null , Decision.COOPERER);
        });

    }
    @Test
    void testSetDecisionJoueur1() {
        tour.setDecisionJoueur1(Decision.COOPERER);
        assertThrows(Exception.class , () ->{
            tour.setDecisionJoueur1(Decision.TRAHIR);
        });
    }

    @Test
    void testSetDecisionJoueur2() {
        tour.setDecisionJoueur2(Decision.COOPERER);
        assertThrows(Exception.class , () ->{
            tour.setDecisionJoueur2(Decision.TRAHIR);
        });
    }

    @Test
    void testEstfini(){
        tour.setDecisionJoueur1(Decision.COOPERER);
        tour.setDecisionJoueur2(Decision.COOPERER);
        tour.calculerGain();
        tour.setEstFini(true);
        assertTrue(tour.estFini());
    }

    @Test
    void testEstfini2(){
        tour.setDecisionJoueur1(Decision.COOPERER);
        assertFalse(tour.estFini());
    }
    @Test
    void testEstfiniNull(){
        assertFalse(tour.estFini());
    }


    @Test
    void testCalculerGain(){

        Tour tour1=new Tour(Decision.COOPERER,Decision.COOPERER);
        assertEquals(ResultatTour.RECOMPENSE, tour1.getGainJoueur1(), "Joueur1 devrait être Recomponsé");
        assertEquals(ResultatTour.RECOMPENSE, tour1.getGainJoueur2(), "Joueur2 devrait être Recomponsé");

        Tour tour4=new Tour(Decision.TRAHIR,Decision.TRAHIR);
        assertEquals(ResultatTour.PUNITION, tour4.getGainJoueur1(), "joueur1 devrait être Punition");
        assertEquals(ResultatTour.PUNITION, tour4.getGainJoueur2(), "joueur2 devrait être Punition");

        Tour tour2=new Tour(Decision.COOPERER,Decision.TRAHIR);
        assertEquals(ResultatTour.DUPERIE, tour2.getGainJoueur1(), "Joueur1 devrait être Duperie");
        assertEquals(ResultatTour.TENTATION, tour2.getGainJoueur2(), "Joueur2 devrait être Tentation" );

        Tour tours3=new Tour(Decision.TRAHIR,Decision.COOPERER);
        assertEquals(ResultatTour.TENTATION, tours3.getGainJoueur1(), "Joueur1 devrait être Tentation");
        assertEquals(ResultatTour.DUPERIE, tours3.getGainJoueur2(), "Joueur2 devrait être Duperie" );

    }

}
