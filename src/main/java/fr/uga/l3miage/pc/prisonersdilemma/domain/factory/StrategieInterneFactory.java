package fr.uga.l3miage.pc.prisonersdilemma.domain.factory;

import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.*;
import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;

public class StrategieInterneFactory {

    private StrategieInterneFactory() {throw new IllegalStateException("Utility class");}



    //methode qui renvois Strategie interne
    public static fr.uga.l3miage.pc.prisonersdilemma.domain.models.Strategie
    creeStrategie(TypeStrategie strategie) {
        return switch (strategie) {
            case DONNANT_DONNANT -> new StrategieDonnantDonnant();
            case PAVLOV -> new StrategiePavlov();
            case PAVLOV_ALEATOIRE -> new StrategiePavlovAleatoire();
            case GRADUEL -> new StrategieGraduel();
            case PACIFICATEUR_NAIF -> new StrategiePacificateurNaif();
            case DONNANT_DONNANT_ALEATOIRE -> new StrategieDonnantDonnantAleatoire();
            case DONNANT_POUR_DEUX_DONNANTS -> new StrategieDonnantPourDeuxDonnants();
            case DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE -> new StrategieDonnantPourDeuxDonnantsAleatoire();
            case ALEATOIRE -> new StrategieAleatoire();
            case TOUJOURS_TRAHIR -> new StrategieToujoursTrahir();
            case TOUJOURS_COOPERER -> new StrategieToujoursCooperer();
            case ADAPTATIF -> new StrategieAdaptatif();
            case DONNANT_DONNANT_SOUPCONNEUX -> new StrategieDonnantDonnantSoupconneux();
            case SONDEUR_NAIF -> new StrategieSondeurNaif();
            case SONDEUR_REPENTANT -> new StrategieSondeurRepentant();
            case VRAI_PACIFICATEUR -> new StrategieVraiPacificateur();
            case RANCUNIER -> new StrategieRancunier();
            case RANCUNIER_DOUX -> new StrategieRancunierDoux();

            default -> throw new IllegalArgumentException("Stratégie inconnue : " + strategie);
        };
    }


}

