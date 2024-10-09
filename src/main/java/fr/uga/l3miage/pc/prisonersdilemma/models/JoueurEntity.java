package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import java.util.List;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@Setter
@Entity
public abstract class JoueurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String nom;
    protected int score;

    protected JoueurEntity() {
    }

    protected JoueurEntity(String nom) {
        this.nom = nom;
        this.score = 0;
    }

    public void ajouterScore(int points) {
        this.score += points;
    }

    public abstract Decision prendreDecision(List<ResultatTourEntity> historique);

}
