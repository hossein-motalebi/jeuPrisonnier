package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
public class ResultatTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Decision decisionJoueur1;

    @Enumerated(EnumType.STRING)
    private Decision decisionJoueur2;

    private int scoreJoueur1;
    private int scoreJoueur2;

    @ManyToOne
    @JoinColumn(name = "partie_id")
    private Partie partie;

    public ResultatTour() {
    }

    public ResultatTour(Decision decisionJoueur1, Decision decisionJoueur2, Partie partie) {
        this.decisionJoueur1 = decisionJoueur1;
        this.decisionJoueur2 = decisionJoueur2;
        this.partie = partie;
        calculerScores();
    }

    private void calculerScores() {
        if (decisionJoueur1 == Decision.COOPERER && decisionJoueur2 == Decision.COOPERER) {
            scoreJoueur1 = 3;
            scoreJoueur2 = 3;
        } else if (decisionJoueur1 == Decision.COOPERER && decisionJoueur2 == Decision.TRAHIR) {
            scoreJoueur1 = 0;
            scoreJoueur2 = 5;
        } else if (decisionJoueur1 == Decision.TRAHIR && decisionJoueur2 == Decision.COOPERER) {
            scoreJoueur1 = 5;
            scoreJoueur2 = 0;
        } else {
            scoreJoueur1 = 1;
            scoreJoueur2 = 1;
        }
    }

}
