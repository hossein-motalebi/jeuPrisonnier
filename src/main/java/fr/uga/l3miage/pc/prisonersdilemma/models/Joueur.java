package fr.uga.l3miage.pc.prisonersdilemma.models;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Joueur {

    private final String nom;
    private int score;

    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
    }
    public void augmenterScore(int score){
        this.score += score;
    }
}
