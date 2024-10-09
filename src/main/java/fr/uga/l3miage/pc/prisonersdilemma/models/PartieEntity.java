package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@Setter
@Entity
public class PartieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private JoueurEntity joueur1;

    @ManyToOne
    private JoueurEntity joueur2;

    @OneToMany(mappedBy = "partie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultatTourEntity> historique = new ArrayList<>();

    private int tourActuel;
    private int totalTours;
    private boolean enCours;

    public PartieEntity() {
    }

    public PartieEntity(JoueurEntity joueur1, JoueurEntity joueur2, int totalTours) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.totalTours = totalTours;
        this.enCours = true;
    }

    public void demarrerPartie() {
        while (enCours && tourActuel < totalTours) {
            jouerTour();
            tourActuel++;
        }
        terminateParties();
    }

    public void jouerTour() {
        Decision decisionJoueur1 = joueur1.prendreDecision(historique);
        Decision decisionJoueur2 = joueur2.prendreDecision(historique);

        ResultatTourEntity resultatTour = new ResultatTourEntity(decisionJoueur1, decisionJoueur2, this);
        historique.add(resultatTour);

        joueur1.ajouterScore(resultatTour.getScoreJoueur1());
        joueur2.ajouterScore(resultatTour.getScoreJoueur2());
    }

    public void terminateParties() {
        enCours = false; // Actions à effectuer en fin de partie
    }

}
