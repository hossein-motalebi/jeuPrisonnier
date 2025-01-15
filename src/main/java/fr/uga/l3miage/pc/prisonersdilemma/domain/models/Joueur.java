package fr.uga.l3miage.pc.prisonersdilemma.domain.models;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString //debug

public abstract class Joueur {

    private String nom;
    private int score;
    protected final int id;

    protected Joueur(String nom, int id) {
        this.nom = nom;
        this.score = 0;
        this.id = id;
    }
    public void augmenterScore(int score){
        this.score += score;
    }

}
