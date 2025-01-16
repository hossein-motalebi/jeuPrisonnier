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
  isGameReady$ = new BehaviorSubject<boolean>(false); // Indique si le jeu est prêt
  idPartie?: number;
  idJoueur?: number;
  private eventSource?: EventSource;

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
    return this.http.get<{ idPartie: number; nomJoueur: string }[]>(`${this.baseUrl}/info-parties`);
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

    if (this.eventSource) {
      this.eventSource.close();
    }

    this.eventSource = new EventSource(`${this.baseUrl}/${this.idPartie}/stream-partie/`);
    this.eventSource.onmessage = (event) => {
      const updatedPartie = JSON.parse(event.data) as OutPartieDTO;

      // Met à jour la partie courante
      this.currentPartie$.next(updatedPartie);

      // Met à jour l'état du jeu
      const isGameReady = updatedPartie.nomJoueur2 !== 'En attend';
      this.isGameReady$.next(isGameReady);
    };

    this.eventSource.onerror = () => {
      console.error('Erreur lors de la connexion SSE.');
      this.eventSource?.close();
    };
  }

  disconnectSSE(): void {
    this.eventSource?.close();
    this.eventSource = undefined;
  }
}
