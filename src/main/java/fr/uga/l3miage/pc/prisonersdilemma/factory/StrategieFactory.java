package fr.uga.l3miage.pc.prisonersdilemma.factory;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.models.Strategie;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.*;

public class StrategieFactory {

    private StrategieFactory() {throw new IllegalStateException("Utility class");}

    public static Strategie creeStrategie(TypeStrategie strategie) {
        return switch (strategie) {
            case DONNANT_DONNANT -> new StrategieDonnantDonnant();
            case DONNANT_DONNANT_ALEATOIRE -> new StrategieDonnantDonnantAleatoire();
            case DONNANT_POUR_DEUX_DONNANTS -> new StrategieDonnantPourDeuxDonnants();
            case DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE -> new StrategieDonnantPourDeuxDonnantsAleatoire();
            case SONDEUR_NAIF -> new StrategieSondeurNaif();
            case SONDEUR_REPENTANT -> new StrategieSondeurRepentant();
            case PACIFICATEUR_NAIF -> new StrategiePacificateurNaif();
            case VRAI_PACIFICATEUR -> new StrategieVraiPacificateur();
            case ALEATOIRE -> new StrategieAleatoire();
            case TOUJOURS_TRAHIR -> new StrategieToujoursTrahir();
            case TOUJOURS_COOPERER -> new StrategieToujoursCooperer();
            case RANCUNIER -> new StrategieRancunier();
            case PAVLOV -> new StrategiePavlov();
            case PAVLOV_ALEATOIRE -> new StrategiePavlovAleatoire();
            case ADAPTATIF -> new StrategieAdaptatif();
            case GRADUEL -> new StrategieGraduel();
            case DONNANT_DONNANT_SOUPCONNEUX -> new StrategieDonnantDonnantSoupconneux();
            case RANCUNIER_DOUX -> new StrategieRancunierDoux();
            default -> throw new IllegalArgumentException("Stratégie inconnue : " + strategie);
        };

    }
}
