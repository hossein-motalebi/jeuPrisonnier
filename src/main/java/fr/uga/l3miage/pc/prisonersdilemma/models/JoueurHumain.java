package fr.uga.l3miage.pc.prisonersdilemma.models;


import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JoueurHumain extends Joueur {

    public JoueurHumain(String nom, int id) {
        super(nom,id);
    }

    public JoueurBot abandonner(TypeStrategie strategie) {
        JoueurBot joueurBot = new JoueurBot(this.getNom()+"(Bot)", strategie , this.getId());
        joueurBot.augmenterScore(this.getScore());
        return joueurBot;

    }
}
