package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;






public class Partie {

   private int nbMaxTours;
   private Joueur joueur1;
   private Joueur joueur2;
   private final Tour[] tours;
   private int currentTourIndex;


    public Partie(int nbMaxTours,Joueur joueur1 , Joueur joueur2){

       setNbMaxTours(nbMaxTours);
       setJoueur1(joueur1);
       setJoueur2(joueur2);
       tours=new Tour[nbMaxTours];
       currentTourIndex = 0;

    }


    // Cette méthode retourne le joueur gagnant une fois que le nombre maximum de tours est atteint.
    // Elle doit être appelée uniquement lorsque le jeu est terminé.
    public Joueur getGagnant(){
        if (currentTourIndex != nbMaxTours && !tours[currentTourIndex - 1].estFini())
            throw new IllegalStateException("le jeu n'est pas encore fini , cette méthode peu être accéder qu'à la fin");

        return joueur1.getScore() > joueur2.getScore() ? joueur1 : joueur2;

    }

   //initalize un tour et l'ajoute dans la liste
   // Initialize a tour and add it to the array
   public void initTour() {
       if (currentTourIndex >= nbMaxTours) {
           throw new IllegalStateException("The number of tours has reached the maximum.");
       }
       if (currentTourIndex != 0 && !tours[currentTourIndex - 1].estFini()) {
           throw new IllegalStateException("le dernier tour n'est pas toujours fini.");
       }
       Tour tourEnCours = new Tour();
       tours[currentTourIndex++] = tourEnCours;
   }

   //mise à jour et finir le tour en cours en enregistrant les décisions des joueurs
    //mise à jour des scores des joueurs
    // soit les deux decision ont passé tant que argument soit juste le méthode
    //est appelé sans argument pour mise à jour des scores
   public void finishTour(Decision... decisions) {

        //on verifie soit il y a 2 decision qui sont passé en argument soit 0 sinon on lance une exception
       if (decisions.length != 0 && decisions.length != 2) {
           throw new IllegalArgumentException("Il faut passer 2 décisions en argument ou aucun.");
       }
       if (currentTourIndex == 0) {
           throw new IllegalStateException("le jeu n'a pas encore commencé.");
       }
       Tour tourEnCours = tours[currentTourIndex - 1];
       if (!tourEnCours.estFini()) {
           throw new IllegalStateException("The last tour is not finished yet.");
       }
       if (decisions.length == 2) {
           tourEnCours.setDecisionJoueur1(decisions[0]);
           tourEnCours.setDecisionJoueur2(decisions[1]);

       }
       updateScores();

    }

    private void updateScores(){
        Tour tourEnCours = tours[currentTourIndex - 1];
        joueur1.augmenterScore(tourEnCours.getGainJoueur1().getPoints());
        joueur2.augmenterScore(tourEnCours.getGainJoueur2().getPoints());
    }

    public void setNbMaxTours(int nbMaxTours){
       if (nbMaxTours <= 0)
           throw new IllegalArgumentException("le Nombre maximum de tours doit être supérieur à zero");
       this.nbMaxTours=nbMaxTours;
    }

    public void setJoueur1(Joueur joueur1){
       if (joueur1 == null)
           throw new IllegalArgumentException("joueur1 ne peut pas être null");
       this.joueur1=joueur1;
    }

    public void setJoueur2(Joueur joueur2){
        if (joueur2 == null)
            throw new IllegalArgumentException("joueur2 ne peut pas être null");
        this.joueur2=joueur2;
    }
}
