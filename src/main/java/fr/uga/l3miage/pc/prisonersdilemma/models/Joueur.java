package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Joueur {

    private final Tour[] tours;
    private final String nom;
    private int score;

    public Joueur(String nom, Tour[] tours) {
        this.nom = nom;
        this.score = 0;
        this.tours = tours;
    }
    public void augmenterScore(int score){
        this.score += score;
    }

}
