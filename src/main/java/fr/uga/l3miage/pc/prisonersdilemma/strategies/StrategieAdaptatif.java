package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.enums.ResultatTour;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

public class StrategieAdaptatif implements Strategie {
    private static final Decision[] SEQUENCE_INITIALE = {
            Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
            Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR
    };

    private double totalGainCooperer = 0;
    private int countCooperer = 0;
    private double totalGainTrahir = 0;
    private int countTrahir = 0;

    @Override
    public Decision deciderTour(Tour[] tours, int idJoueur, int idAdversaire) {
        int tourActuel = tours != null ? tours.length : 0;

        // Mettre à jour les gains basés sur les tours précédents
        if (tours != null && tours.length > 0) {
            Tour dernierTour = tours[tours.length - 1];
            Decision decisionPrecedente = dernierTour.getDecisionJoueur(idJoueur);
            double gainDernierTour = obtenirGainNumerique(dernierTour.getGainJoueur(idJoueur));

            if (decisionPrecedente == Decision.COOPERER) {
                totalGainCooperer += gainDernierTour;
                countCooperer++;
            } else if (decisionPrecedente == Decision.TRAHIR) {
                totalGainTrahir += gainDernierTour;
                countTrahir++;
            }
        }

        // Séquence initiale
        if (tourActuel < SEQUENCE_INITIALE.length) {
            return SEQUENCE_INITIALE[tourActuel];
        } else {
            // Choix adaptatif basé sur le gain moyen
            double moyenneCooperer = countCooperer > 0 ? totalGainCooperer / countCooperer : 0;
            double moyenneTrahir = countTrahir > 0 ? totalGainTrahir / countTrahir : 0;

            if (moyenneCooperer > moyenneTrahir) {
                return Decision.COOPERER;
            } else if (moyenneTrahir > moyenneCooperer) {
                return Decision.TRAHIR;
            } else {
                // En cas d'égalité, coopérer par défaut
                return Decision.COOPERER;
            }
        }
    }

    private double obtenirGainNumerique(ResultatTour resultat) {
        return switch (resultat) {
            case RECOMPENSE -> 3;
            case TENTATION -> 5;
            case PUNITION -> 1;
            case DUPERIE -> 0;
        };
    }
}
