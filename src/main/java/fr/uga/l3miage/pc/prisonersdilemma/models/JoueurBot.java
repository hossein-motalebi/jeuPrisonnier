package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.factory.StrategieFactory;
import lombok.Getter;


@Getter

public class JoueurBot extends Joueur {

    private final TypeStrategie typeStrategie;
    private final Strategie strategie;

    public JoueurBot(String nom , TypeStrategie typeStrategie){
        super(nom);
        if (typeStrategie == null) {
            throw new IllegalArgumentException("TypeStrategie cannot be null");
        }

        this.typeStrategie=typeStrategie;
        this.strategie= StrategieFactory.creeStrategie(typeStrategie);
    }

    public Decision prendreDecision(Tour[] tours){
        return strategie.deciderTour(tours);
    }





}