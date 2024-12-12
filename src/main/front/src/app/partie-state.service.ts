import { Injectable } from '@angular/core';
import { PartieDTO } from './outils';

@Injectable({ providedIn: 'root' })
export class PartieStateService {
  private partie?: PartieDTO;

  setPartie(p: PartieDTO) {
    this.partie = p;
  }

  getPartie(): PartieDTO | undefined {
    return this.partie;
  }
}
