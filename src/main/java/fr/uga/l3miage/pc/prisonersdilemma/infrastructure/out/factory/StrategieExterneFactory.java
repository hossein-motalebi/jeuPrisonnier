package fr.uga.l3miage.pc.prisonersdilemma.infrastructure.out.factory;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.TypeStrategie;
import fr.uga.strats.g8.strategie.*;

import java.security.SecureRandom;

public class StrategieExterneFactory {

    private StrategieExterneFactory() {throw new IllegalStateException("Utility class");}

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
