package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.ResultatTour;
import lombok.Getter;

/*
* Class Tour peut utiliser de deux façons :
* 1. Tour tour = new Tour(); //pour créer un tour sans décision des joueurs
* et on peut ajouter les décisions des joueurs plus tard
* 2. Tour tour = new Tour(decisionJoueur1, decisionJoueur2); //pour créer un tour avec les décisions des joueurs
*
* les gagnants des joueurs sont calculés automatiquement en utilisant la méthode CalculerGain() après avoir défini les décisions des joueurs
* et ils ne sont pas accessibles pour modification ni setter manuellement
* */
@Getter

public class Tour {
    private Decision decisionJoueur1;
    private Decision decisionJoueur2;
    private ResultatTour gainJoueur1;
    private ResultatTour gainJoueur2;
    private boolean estFini=false;

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
        if (decisionJoueur1 == null) {
            throw new IllegalArgumentException("Decision ne peut pas être null.");
        }
        if (this.decisionJoueur1 != null) {
            throw new IllegalStateException("La décision du joueur 1 est déjà définie");
        }
        this.decisionJoueur1 = decisionJoueur1;
    }

    public void setDecisionJoueur2(Decision decisionJoueur2) {
        if (decisionJoueur2 == null) {
            throw new IllegalArgumentException("Decision ne peut pas être null.");
        }
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


    protected void setEstFini(boolean estFini) {
        this.estFini = estFini;
    }

    public boolean estFini(){
        return estFini;
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
