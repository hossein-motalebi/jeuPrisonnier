import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { InitPartieDTO } from '../models/init-partie-dto';
import { OutPartieDTO } from '../models/out-partie-dto';
import { DecisionDTO } from '../models/decision-dto';

@Injectable({
  providedIn: 'root',
})
export class PartieService {
  private readonly baseUrl = 'http://localhost:8080/jeu'; // URL de votre backend
  currentPartie$ = new BehaviorSubject<OutPartieDTO | null>(null);
  idPartie?: number;
  idJoueur?: number;

  constructor(private readonly http: HttpClient) {}

  demarrerPartie(dto: InitPartieDTO): void {
    this.http.post<{ idPartie: number }>(`${this.baseUrl}/init-partie`, dto).subscribe({
      next: (res) => {
        this.idPartie = res.idPartie;
        this.idJoueur = 1; // Joueur 1 crée la partie
        this.connectSSE(); // Connexion SSE après création
      },
      error: (err) => console.error('Erreur lors de la création de la partie :', err),
    });
  }

  obtenirPartiesEnCours() {
    return this.http.get<{ idPartie: number; adversaire: string }[]>(`${this.baseUrl}/info-parties`);
  }

  rejoindrePartie(idPartie: number, nomJoueur: string): void {
    this.http.post<void>(`${this.baseUrl}/${idPartie}/join-partie/`, { nomJoueur }).subscribe({
      next: () => {
        this.idPartie = idPartie;
        this.idJoueur = 2; // Joueur 2 rejoint la partie
        this.connectSSE(); // Connexion SSE après jonction
      },
      error: (err) => console.error('Erreur lors de la jonction de la partie :', err),
    });
  }

  jouerTour(decisionDto: DecisionDTO): void {
    if (!this.idPartie || !this.idJoueur) {
      console.error('ID de partie ou de joueur manquant.');
      return;
    }

    this.http
      .post<void>(`${this.baseUrl}/${this.idPartie}/jouer-tour/`, {
        ...decisionDto,
        idJoueur: this.idJoueur,
      })
      .subscribe({
        error: (err) => console.error('Erreur lors du tour de jeu :', err),
      });
  }

  connectSSE(): void {
    if (!this.idPartie) {
      console.error('ID de la partie non défini pour les SSE.');
      return;
    }

    const eventSource = new EventSource(`${this.baseUrl}/${this.idPartie}/stream-partie/`);
    eventSource.onmessage = (event) => {
      const updatedPartie = JSON.parse(event.data) as OutPartieDTO;
      this.currentPartie$.next(updatedPartie);

      if (updatedPartie.nomJoueur2 !== 'En attend') {
        console.log('Partie prête à jouer.');
      }
    };

    eventSource.onerror = () => {
      console.error('Erreur lors de la connexion SSE.');
      eventSource.close();
    };
  }
}
