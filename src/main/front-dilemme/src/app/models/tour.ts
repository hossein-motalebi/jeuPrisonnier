export interface Tour {
  decisionJoueur1: 'COOPERER' | 'TRAHIR' | null;
  decisionJoueur2: 'COOPERER' | 'TRAHIR' | null;
  gainJoueur1: number;
  gainJoueur2: number;
}
