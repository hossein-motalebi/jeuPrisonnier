import exp from 'node:constants';

export enum Decision {
  COOPERER = 'COOPERER',
  TRAHIR = 'TRAHIR'
}

export enum TypeStrategie {
  DONNANT_DONNANT = 'DONNANT_DONNANT',
  DONNANT_DONNANT_ALEATOIRE = 'DONNANT_DONNANT_ALEATOIRE',
  DONNANT_POUR_DEUX_DONNANTS = 'DONNANT_POUR_DEUX_DONNANTS',
  DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE = 'DONNANT_POUR_DEUX_DONNANTS_ALEATOIRE',
  SONDEUR_NAIF = 'SONDEUR_NAIF',
  SONDEUR_REPENTANT = 'SONDEUR_REPENTANT',
  PACIFICATEUR_NAIF = 'PACIFICATEUR_NAIF',
  VRAI_PACIFICATEUR = 'VRAI_PACIFICATEUR',
  ALEATOIRE = 'ALEATOIRE',
  TOUJOURS_TRAHIR = 'TOUJOURS_TRAHIR',
  TOUJOURS_COOPERER = 'TOUJOURS_COOPERER',
  RANCUNIER = 'RANCUNIER',
  PAVLOV = 'PAVLOV',
  PAVLOV_ALEATOIRE = 'PAVLOV_ALEATOIRE',
  ADAPTATIF = 'ADAPTATIF',
  GRADUEL = 'GRADUEL',
  DONNANT_DONNANT_SOUPCONNEUX = 'DONNANT_DONNANT_SOUPCONNEUX',
  RANCUNIER_DOUX = 'RANCUNIER_DOUX'
}

export enum ResultatTour {
  TENTATION = 5,   // T
  RECOMPENSE = 3,  // C
  PUNITION = 1,    // P
  DUPERIE = 0      // D
}

// Classes du back adaptées en TS pour le front

export class Tour {
  decisionJoueur1!: Decision;
  decisionJoueur2!: Decision;
  gainJoueur1!: ResultatTour;
  gainJoueur2!: ResultatTour;
  estFini!: boolean;
}

// Joueur : Joueur est abstrait dans le back, ici on peut faire une classe de base
export abstract class Joueur {
  nom!: string;
  score!: number;
  id!: number;
}

// JoueurHumain hérite de Joueur
export class JoueurHumain extends Joueur {
  // Pas de champ supplémentaire
}

// JoueurBot hérite de Joueur et ajoute une stratégie
export class JoueurBot extends Joueur {
  typeStrategie!: TypeStrategie;
}

// Partie
export class Partie {
  nbMaxTours!: number;
  joueur1!: Joueur;
  joueur2!: Joueur;
  // tours complet
  tours!: Tour[];
  currentTourIndex!: number;
}

// OutPartieDTO correspond à ce que le backend renvoie après démarrage ou après un tour,
// C’est un objet qu’on va recevoir du backend, incluant toutes les infos nécessaires
export class PartieDTO {
  nomJoueur1!: string;
  nomJoueur2!: string;
  idPlayer1!: number;
  idPlayer2!: number;
  joueur1bot!: boolean;
  joueur2bot!: boolean;
  strategieJoueur1?: TypeStrategie;
  strategieJoueur2?: TypeStrategie;
  nbTourLeft!: number;
  scoreJoueur1!: number;
  scoreJoueur2!: number;
  historique!: Tour[];
}

export class DecisionDTO {
  decisionJoueur1!: Decision;
  decisionJoueur2!: Decision;
}
