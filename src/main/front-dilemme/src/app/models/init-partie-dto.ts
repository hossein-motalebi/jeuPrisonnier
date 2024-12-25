import { TypeStrategie } from './type-strategie';

export interface InitPartieDTO {
    nomJoueur1: string;
    nomJoueur2: string;
    joueur1bot: boolean;
    joueur2bot: boolean;
    strategieJoueur1: TypeStrategie | undefined;
    strategieJoueur2: TypeStrategie | undefined ;
    strategieExterneJoueur1: boolean;
    strategieExterneJoueur2: boolean;
    nbMaxTours: number;
  }
