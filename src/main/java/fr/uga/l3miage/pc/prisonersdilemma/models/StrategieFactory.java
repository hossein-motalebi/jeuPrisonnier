package fr.uga.l3miage.pc.prisonersdilemma.models;

import fr.uga.l3miage.pc.prisonersdilemma.strategies.StrategieAleatoire;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.StrategieDonnantDonnant;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.StrategieRancunier;

public class StrategieFactory {

    private StrategieFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Strategie getStrategie(String strategieNom) {
        if (strategieNom == null) {
            return null;
        }
        return switch (strategieNom) {
            case "StrategieDonnantDonnant" -> new StrategieDonnantDonnant();
            case "StrategieRancunier" -> new StrategieRancunier();
            case "StrategieAleatoire" -> new StrategieAleatoire();
            // Ajouter les autres stratégies ici
            default -> throw new IllegalArgumentException("Stratégie inconnue : " + strategieNom);
        };
    }
}
