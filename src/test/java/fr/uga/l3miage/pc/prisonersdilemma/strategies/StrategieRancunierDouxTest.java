package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;
import org.junit.jupiter.api.Test;

class StrategieRancunierDouxTest {

    private Tour[] ajouterTourAvecDecisionJoueur1EtAdversaire(Tour[] tours, Decision decisionJoueur1, Decision decisionAdversaire) {
        Tour[] nouveauxTours = new Tour[tours.length + 1];
        System.arraycopy(tours, 0, nouveauxTours, 0, tours.length);
        nouveauxTours[tours.length] = new Tour(decisionJoueur1, decisionAdversaire);
        return nouveauxTours;
    }


    @Test
     void testCooperationInitiale() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = new Tour[0];

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer au premier tour.");
    }

    @Test
     void testCooperationTourUn() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer");
    }

    @Test
     void testCooperationTourDeux() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.COOPERER)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.COOPERER, decision, "La stratégie devrait coopérer");
    }

    @Test
     void testPunitionTourUn() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };

        Decision decision = strategie.deciderTour(tours, 1, 2);

        assertEquals(Decision.TRAHIR, decision, "La stratégie devrait Trahir");
    }

    @Test
     void testPunitionApresTrahisonAdversaire() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        // Début : un seul tour (C,C)
        Tour[] tours = { new Tour(Decision.COOPERER, Decision.COOPERER) };
        // On met à jour la stratégie (pas obligatoire, mais cohérent)
        strategie.deciderTour(tours, 1, 2);

        // L'adversaire trahit au deuxième tour
        // Avant d'ajouter ce tour, on récupère la décision du joueur 1
        Decision decisionJ1 = strategie.deciderTour(tours, 1, 2);
        // Au début, la stratégie coopère toujours tant que pas de trahison détectée, donc COOPERER
        // On ajoute le tour (COOPERER, TRAHIR)
        tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionJ1, Decision.TRAHIR);

        // Maintenant, l'adversaire a trahi. La punition (6 décisions) doit commencer.
        // Séquence de punition : t,t,t,t,c,c (6 décisions)
        Decision[] sequenceAttendue = {
                Decision.TRAHIR,
                Decision.TRAHIR,
                Decision.TRAHIR,
                Decision.TRAHIR,
                Decision.COOPERER,
                Decision.COOPERER
        };

        // On exécute les 6 décisions de punition une par une
        for (int i = 0; i < sequenceAttendue.length; i++) {
            Decision decisionPunition = strategie.deciderTour(tours, 1, 2);
            assertEquals(sequenceAttendue[i], decisionPunition, "La décision de punition à l'étape " + (i+1) + " devrait être " + sequenceAttendue[i]);

            // On suppose que l'adversaire coopère pendant toute la punition
            tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionPunition, Decision.COOPERER);
        }

        // Après avoir exécuté les 6 étapes de la punition, la stratégie doit être revenue à un état normal.
        // On appelle deciderTour avec l'adversaire qui coopère, la stratégie devrait coopérer.
        Decision decisionApresPunition = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decisionApresPunition, "La stratégie devrait coopérer après la punition si l'adversaire coopère.");

        // On ajoute ce dernier tour (COOPERER, COOPERER)
        tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionApresPunition, Decision.COOPERER);

        // Vérification supplémentaire : la stratégie doit continuer à coopérer
        Decision decisionFinal = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decisionFinal, "La stratégie devrait continuer à coopérer après la punition si l'adversaire continue à coopérer.");
    }

    @Test
     void testRepunitionSiAdversaireRetrahit() {
        StrategieRancunierDoux strategie = new StrategieRancunierDoux();

        // tours initiaux : (C,C), (C,T)
        Tour[] tours = {
                new Tour(Decision.COOPERER, Decision.COOPERER),
                new Tour(Decision.COOPERER, Decision.TRAHIR)
        };

        // Première séquence de punition (6 décisions)
        // Même logique : on récupère la décision à chaque étape, on ajoute le tour correspondant
        for (int i = 0; i < 6; i++) {
            Decision decisionPunition = strategie.deciderTour(tours, 1, 2);
            // Adversaire coopère pendant la punition
            tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionPunition, Decision.COOPERER);
        }

        // Après la punition, si l'adversaire coopère, la stratégie doit coopérer
        Decision decisionApresPunition = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.COOPERER, decisionApresPunition, "La stratégie devrait coopérer après la punition si l'adversaire coopère.");

        // On ajoute ce tour (COOPERER, COOPERER)
        tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionApresPunition, Decision.COOPERER);
        Decision decisionAvantRePunition = strategie.deciderTour(tours, 1, 2);

        tours = ajouterTourAvecDecisionJoueur1EtAdversaire(tours, decisionAvantRePunition, Decision.TRAHIR);

        Decision decisionRepunition = strategie.deciderTour(tours, 1, 2);
        assertEquals(Decision.TRAHIR, decisionRepunition, "La stratégie devrait recommencer la punition après une nouvelle trahison de l'adversaire.");
    }



}