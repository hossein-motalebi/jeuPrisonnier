package fr.uga.l3miage.pc.prisonersdilemma.factory;

import fr.uga.l3miage.pc.prisonersdilemma.enums.TypeStrategie;
import fr.uga.l3miage.pc.prisonersdilemma.strategies.*;
import fr.uga.strats.g8.strategie.*;

import java.security.SecureRandom;

public class StrategieFactory {

    private StrategieFactory() {throw new IllegalStateException("Utility class");}

    /*
    *a ajouté une fois les stratégies implémentées
    *case RANCUNIER -> new StrategieRancunier(); //NOSONAR
    *case RANCUNIER_DOUX -> new StrategieRancunierDoux(); //NOSONAR
     */

    //methode qui renvois Strategie interne
    public static fr.uga.l3miage.pc.prisonersdilemma.models.Strategie
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


    //methode qui renvois Strategie externe
    public static Strategie creeStrategieExtern(TypeStrategie strategie) {
        return switch (strategie) {
            case DONNANT_DONNANT -> new DonnantDonnantStrategie();
            case PAVLOV -> new PavlovStrategie();
            case PAVLOV_ALEATOIRE -> new PavlovAleatoireStrategie(new SecureRandom());
            case GRADUEL -> new GraduelStrategie();
            case PACIFICATEUR_NAIF -> new PacificateurNaifStrategie(new SecureRandom());
            case DONNANT_DONNANT_ALEATOIRE -> new DonnantDonnantAleatoireStrategie(new SecureRandom());
            case DONNANT_POUR_DEUX_DONNANTS -> new DonnantPourDeuxDonnantsStrategie();
            case DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE ->
                    new DonnantPourDeuxDonnantsEtAleatoireStrategie(new SecureRandom(), new SecureRandom());
            case ALEATOIRE -> new AleatoireStrategie(new SecureRandom());
            case TOUJOURS_TRAHIR -> new ToujoursTrahirStrategie();
            case TOUJOURS_COOPERER -> new ToujoursCoopererStrategie();
            case ADAPTATIF -> new AdaptatifStrategie();
            case DONNANT_DONNANT_SOUPCONNEUX -> new DonnantDonnantSoupconneuxStrategie();
            case SONDEUR_NAIF -> new SondeurNaifStrategie(new SecureRandom());
            case SONDEUR_REPENTANT -> new SondeurRepentantStrategie(new SecureRandom());
            case VRAI_PACIFICATEUR -> new VraiPacificateurStrategie(new SecureRandom());
            case RANCUNIER -> new RancunierStrategie();
            case RANCUNIER_DOUX -> new RancunierDouxStrategie();
            default -> throw new IllegalArgumentException("Stratégie Externe inconnue : " + strategie);
        };

    }

}

