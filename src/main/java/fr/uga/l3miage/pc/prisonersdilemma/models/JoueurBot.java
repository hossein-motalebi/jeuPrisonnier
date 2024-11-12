package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import lombok.Getter;
import lombok.Setter;

@Getter

public class JoueurBot extends Joueur {

    private TypeStrategie typeStrategie;
    private final Strategie strategie;

    public JoueurBot(String nom , TypeStrategie typeStrategie , Tour[] tours){
        super(nom,tours);
        if (typeStrategie == null) {
            throw new IllegalArgumentException("TypeStrategie cannot be null");
        }
        if (tours == null) {
            throw new IllegalArgumentException("Tours cannot be null");
        }
        this.typeStrategie=typeStrategie;
        this.strategie= StrategieFactory.creeStrategie(typeStrategie);
    }

    public Decision prendreDecision(){
        return strategie.deciderTour(super.getTours());
    }





}