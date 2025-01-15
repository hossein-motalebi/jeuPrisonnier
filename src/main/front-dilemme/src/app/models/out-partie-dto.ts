import {Tour} from "./tour";

export interface OutPartieDTO {
  nomJoueur1: string;
  nomJoueur2: string;
  idPlayer1: number;
  idPlayer2: number;
  joueur1bot: boolean;
  joueur2bot: boolean;
  nbTourLeft: number;
  scoreJoueur1: number;
  scoreJoueur2: number;
  historique: Tour[];
}
