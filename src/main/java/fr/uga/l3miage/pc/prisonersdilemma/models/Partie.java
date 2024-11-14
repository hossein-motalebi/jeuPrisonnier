package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;




/* il y a deux possibilité de implementer la logique de jeu en utilisant
    * la classe Partie
    * 1. en initialisant un tour en passe les 2 decision en argument
    *    dans ce cas le tour va être initialisé avec les deux décisions
    *    et les calculs des scores vont être fait automatiquement
    *    ensuite il faut faire appele à partie.finishTour() pour finir le tour
    *    qu'il est imperative pour mise à jour des scores de chaque joueur
    * 2. en initialisant un tour sans passer les décisions en argument
    *    dans ce que il faut collecter les décisions des joueurs et les passer
    *    en argument de finishTour() pour mise à jour des scores
    *
    * pour sommaire soit
    *   partie.initTour(decision1,decision2) en suite partie.finishTour()
    * soit
    *   partie.initTour() et ensuite  partie.finishTour(decision1,decision2)
 */


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
    public int getGagnant(){
        if (currentTourIndex != nbMaxTours )
            throw new IllegalStateException("le jeu n'est pas encore fini , cette méthode peu être accéder qu'à la fin");

        return joueur1.getScore() == joueur2.getScore() ? 0
                : joueur1.getScore() > joueur2.getScore() ? 1 : 2;

    }

   //initalize un tour et l'ajoute dans la liste
   // Initialize a tour and add it to the array
   public void initTour(Decision... decisions) {
       Tour tourEnCours;
       if (currentTourIndex >= nbMaxTours) {
           throw new IllegalStateException("The number of tours has reached the maximum.");
       }

       if (tours[currentTourIndex] != null && !tours[currentTourIndex].estFini()) {
           throw new IllegalStateException("le dernier tour n'est pas toujours fini.");
       }
       if (decisions.length != 0 && decisions.length != 2) {
           throw new IllegalArgumentException("Il faut passer 2 décisions en argument ou aucun.");
       }
       if (decisions.length == 2) {
            tourEnCours= new Tour(decisions[0], decisions[1]);
       }
       else{
            tourEnCours = new Tour();}
       tours[currentTourIndex] = tourEnCours;
   }

   //mise à jour et finir le tour en cours en enregistrant les décisions des joueurs
    //mise à jour des scores des joueurs
    // soit les deux decision ont passé tant qu'argument soit juste la méthode
    //est appelé sans argument pour mise à jour des scores
   public void finishTour(Decision... decisions) {
       Tour tourEnCours = tours[currentTourIndex];

       //on vérifie soit il y a 2 decision qui sont passé en argument soit 0 sinon on lance une exception
       if (decisions.length != 0 && decisions.length != 2) {
           throw new IllegalArgumentException("Il faut passer 2 décisions en argument ou aucun.");
       }
       if (tourEnCours==null){
           throw new IllegalStateException("initTour() n'est pas encore applée");
       }

       if (currentTourIndex == 0 && decisions.length == 0 &&(tours[0].getDecisionJoueur1()==null || tours[0].getDecisionJoueur2() == null)) {
           throw new IllegalStateException("initTour() n'est pas encore applée");
       }

       if (decisions.length == 2) {
           tourEnCours.setDecisionJoueur1(decisions[0]);
           tourEnCours.setDecisionJoueur2(decisions[1]);
           tourEnCours.calculerGain();

       }else{
           if (tourEnCours.getDecisionJoueur1() == null || tourEnCours.getDecisionJoueur2() == null) {
               throw new IllegalStateException("les Decision des jours dans tour ne sont pas encore définies");
           }
       }
       updateScores();
       tourEnCours.setEstFini(true);
       currentTourIndex++;
    }

    /* envois un copy de tours de partie, si des tours ne sont pas terminé, il n'est pas envoyé*/
    protected Tour[] getToursCopy(){
        Tour[] toursEnCours = new Tour[currentTourIndex];
        int i = 0;
        while( i < currentTourIndex && tours[i].estFini()){
            toursEnCours[i] = tours[i];
            i++;
        }
        return toursEnCours;
    }

    private void updateScores(){
        Tour tourEnCours = tours[currentTourIndex];
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

