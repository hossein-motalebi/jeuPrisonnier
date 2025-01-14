package fr.uga.l3miage.pc.prisonersdilemma.domain.models;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import lombok.Getter;

@Getter

public class JoueurBot extends Joueur {

    private final Strategie strategie;

    public JoueurBot(String nom ,Strategie strategie, int id) {
        super(nom,id);
        if (strategie == null) {
            throw new IllegalArgumentException("Strategie cannot be null");
        }
        this.strategie=strategie;
    }

    public Decision prendreDecision(Tour[] tours){
        return strategie.deciderTour(tours,this.id,3-this.id);
    }





}