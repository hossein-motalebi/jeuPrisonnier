package fr.uga.l3miage.pc.prisonersdilemma.domain.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.domain.models.Tour;

import java.security.SecureRandom;

public class StrategieVraiPacificateur implements Strategie {

    int trahisonConsecutive;

    private SecureRandom random;

    protected void setRandom(SecureRandom random) {
        this.random = random;
    }

    public boolean parFois(){
        return this.random.nextInt(100) > 75;

    }

    public  StrategieVraiPacificateur(){
        trahisonConsecutive=0;
        setRandom(new SecureRandom());
    }

    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        if (tours.length==0)
            return Decision.COOPERER;
        Decision desPrecedentAdv=tours[tours.length-1].getDecisionJoueur(idAdversaire);
        if (desPrecedentAdv==Decision.TRAHIR)
            trahisonConsecutive++;
        else {
            trahisonConsecutive=0;
            return Decision.COOPERER;
        }
        //jusqu'à ici le joueur à trahir
        if (trahisonConsecutive <2)
            return Decision.COOPERER;
        //ici il a trahir au moins 2 fois
        if(parFois())
            return Decision.COOPERER;
        return Decision.TRAHIR;

    }


}
