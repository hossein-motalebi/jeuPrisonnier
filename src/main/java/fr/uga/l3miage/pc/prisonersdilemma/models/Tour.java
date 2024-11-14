package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.ResultatTour;
import lombok.Getter;
import lombok.Setter;

@Getter

public class Tour {
    private Decision decisionJoueur1;
    private Decision decisionJoueur2;
    private ResultatTour gainJoueur1;
    private ResultatTour gainJoueur2;

    //constructeur par défault
    public Tour(){
    }

    //constructeur avec decision des joueurs en parametre
    //on initialise les decisions des joueurs et on calcule gainJoueur1 et gainJoueur2
    public Tour(Decision decisionJoueur1, Decision decisionJoueur2) {
        setDecisionJoueur1(decisionJoueur1);
        setDecisionJoueur2(decisionJoueur2);
        calculerGain();
    }

    public void setDecisionJoueur1(Decision decisionJoueur1) {
        if (this.decisionJoueur1 != null) {
            throw new IllegalStateException("La décision du joueur 1 est déjà définie");
        }
        this.decisionJoueur1 = decisionJoueur1;
    }

    public void setDecisionJoueur2(Decision decisionJoueur2) {
        if (this.decisionJoueur2 != null) {
            throw new IllegalStateException("La décision du joueur 1 est déjà définie");
        }
        this.decisionJoueur2 = decisionJoueur2;
    }

    private void setGainJoueur1(ResultatTour gainJoueur1) {
        if (this.gainJoueur1 != null) {
            throw new IllegalStateException("Le gain du joueur 1 est déjà défini");
        }
        this.gainJoueur1 = gainJoueur1;
    }

    private void setGainJoueur2(ResultatTour gainJoueur2) {
        if (this.gainJoueur2 != null) {
            throw new IllegalStateException("Le gain du joueur 2 est déjà défini");
        }
        this.gainJoueur2 = gainJoueur2;
    }



    public boolean estFini(){
        return gainJoueur1 != null && gainJoueur2 != null
                && decisionJoueur1 != null && decisionJoueur2 != null;
    }

    public void calculerGain(){
        if (decisionJoueur1 == null || decisionJoueur2 == null) {
            throw new IllegalStateException("Les décisions des joueurs ne sont pas encore définies");
        }
        if (decisionJoueur1 == Decision.COOPERER && decisionJoueur2 == Decision.COOPERER) {
            setGainJoueur1(ResultatTour.RECOMPENSE); //chaque gain est de 3 points
            setGainJoueur2(ResultatTour.RECOMPENSE);
        } else if (decisionJoueur1 == Decision.TRAHIR && decisionJoueur2 == Decision.TRAHIR) {
            setGainJoueur1(ResultatTour.PUNITION); //chaque gain est de 1 point
            setGainJoueur2(ResultatTour.PUNITION);
        } else if (decisionJoueur1 == Decision.TRAHIR && decisionJoueur2 == Decision.COOPERER) {
            setGainJoueur1(ResultatTour.TENTATION); //celle qui traite gagne 5 points et l'autre 0
            setGainJoueur2(ResultatTour.DUPERIE);
        } else { //decisionJoueur1 == Decision.COOPERER && decisionJoueur2 == Decision.TRAHIR
            setGainJoueur1(ResultatTour.DUPERIE);
            setGainJoueur2(ResultatTour.TENTATION);
        }
    }


}
