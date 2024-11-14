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

    private int parcourTrahir(){

    }

    @Override
    public Decision deciderTour(Tour[] tours){
        if (tours.length == 0){
            return Decision.COOPERATE;
        }
        if (tours[tours.length-1])
    }
}
