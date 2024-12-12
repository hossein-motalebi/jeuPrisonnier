import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PartieDTO } from './outils';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {environment} from './environment';

@Injectable({providedIn:'root'})
export class BackendService {
  constructor(private http: HttpClient) {}

  demarrerPartie(nomJ1: string, nomJ2: string, nbTours: number, joueur1bot=false, joueur2bot=false): Observable<PartieDTO> {
    if (environment.useMockBackend) {
      // Renvoie une partie simulée
      const mockPartie: PartieDTO = {
        nomJoueur1: nomJ1,
        nomJoueur2: nomJ2,
        idPlayer1: 1,
        idPlayer2: 2,
        joueur1bot,
        joueur2bot,
        nbTourLeft: nbTours,
        scoreJoueur1: 0,
        scoreJoueur2: 0,
        historique: []
      };
      return of(mockPartie);
    } else {
      // Appel réel backend
      const body = {
        nomJoueur1: nomJ1,
        nomJoueur2: nomJ2,
        nbMaxTours: nbTours,
        joueur1bot,
        joueur2bot
      };
      return this.http.post<PartieDTO>('/jeu/demarrer', body).pipe(
        catchError(err => {
          console.error('Erreur backend, passage en mock', err);
          const mockPartie: PartieDTO = {
            nomJoueur1: nomJ1,
            nomJoueur2: nomJ2,
            idPlayer1: 1,
            idPlayer2: 2,
            joueur1bot,
            joueur2bot,
            nbTourLeft: nbTours,
            scoreJoueur1: 0,
            scoreJoueur2: 0,
            historique: []
          };
          return of(mockPartie);
        })
      );
    }
  }
}
