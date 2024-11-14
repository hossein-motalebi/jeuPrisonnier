package fr.uga.l3miage.pc.prisonersdilemma.models;


import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JoueurHumain extends Joueur {

    public JoueurHumain(String nom){
        super(nom);

    }

    public JoueurBot abandonner(TypeStrategie strategie, Tour[] tours) {
        return new JoueurBot(this.getNom()+"(Bot)", strategie);

    }
}
