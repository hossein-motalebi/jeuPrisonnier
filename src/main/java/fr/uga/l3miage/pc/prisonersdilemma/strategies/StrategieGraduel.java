package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieGraduel implements Strategie {

    boolean cycleTrahis ;
    int reponseDeTrahir;
    int coopeConsecutif;

    public StrategieGraduel(){
        cycleTrahis = false;
        reponseDeTrahir = 0;
        coopeConsecutif = 0;
    }

    private int parcourTrahir(Tour[] tours , int idAdversaire){

        int trahison = 0;

        for (int i = 0 ; i < tours.length ; i++) {
            if (tours[i].getDecisionJoueur(idAdversaire)==Decision.TRAHIR){
                trahison++;
            }
        }
        return trahison;

    }

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours.length == 0){
            return Decision.COOPERER;
        }
        if (cycleTrahis==false && tours[tours.length-1].getDecisionJoueur(idAdversaire)==Decision.TRAHIR){
            // on fait un cycle de trahison
            cycleTrahis = true;
            reponseDeTrahir = parcourTrahir(tours,idAdversaire)-1;
            return Decision.TRAHIR;
        }
        if (cycleTrahis==true && reponseDeTrahir>0){
            reponseDeTrahir--;
            return Decision.TRAHIR;
        }
        else if (coopeConsecutif<2){
            coopeConsecutif++;
            return Decision.COOPERER;
        }
        else {
            coopeConsecutif = 0;
            cycleTrahis = false;
            return Decision.COOPERER;
        }

    }
}
