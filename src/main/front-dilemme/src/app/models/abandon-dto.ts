import { TypeStrategie } from './type-strategie';

export interface AbandonnerDTO {
  idPlayer: number;
  strategie: TypeStrategie;
  strategieExterne: boolean;
}
