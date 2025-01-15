import {TypeStrategie} from "./TypeStrategie";

export interface Joueur {
  id: number;
  nom: string;
  bot: boolean;
  strategie?: TypeStrategie;
}
