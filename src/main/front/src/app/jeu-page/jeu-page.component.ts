import { Component, OnInit } from '@angular/core';
import { Decision, PartieDTO, Tour } from '../outils';
import { PartieStateService } from '../partie-state.service';
import {MatIcon} from '@angular/material/icon';
import {MatButton} from '@angular/material/button';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-jeu-page',
  standalone: true,
  templateUrl: './jeu-page.component.html',
  imports: [
    MatIcon,
    MatButton,
    NgIf,
    NgForOf
  ],
  styleUrls: ['./jeu-page.component.css']
})
export class JeuPageComponent implements OnInit {
  partie!: PartieDTO; // Partie actuelle
  decisionJoueur1?: Decision;
  decisionJoueur2?: Decision;

  constructor(private partieState: PartieStateService) {}

  ngOnInit(): void {
    const p = this.partieState.getPartie();
    if (!p) {
      console.error('Aucune partie disponible, retour à la page start');
      // Redirection possible si le router est injecté
      return;
    }
    this.partie = p;

    // Si le joueur est un bot, définir une décision automatique par défaut
    if (this.partie.joueur1bot) {
      this.decisionJoueur1 = Decision.COOPERER; // Exemple de stratégie par défaut
    }
    if (this.partie.joueur2bot) {
      this.decisionJoueur2 = Decision.COOPERER; // Exemple de stratégie par défaut
    }
  }

  // Vérifie si le joueur a déjà pris sa décision
  get joueur1PeutJouer(): boolean {
    return !this.partie.joueur1bot && !this.decisionJoueur1;
  }

  get joueur2PeutJouer(): boolean {
    return !this.partie.joueur2bot && !this.decisionJoueur2;
  }

  // Vérifie si tous les joueurs ont pris leur décision
  get peutAvancer(): boolean {
    return !!this.decisionJoueur1 && !!this.decisionJoueur2;
  }

  // Définit la décision pour un joueur
  prendreDecision(joueur: 'joueur1' | 'joueur2', decision: Decision) {
    if (joueur === 'joueur1' && this.joueur1PeutJouer) {
      this.decisionJoueur1 = decision;
    } else if (joueur === 'joueur2' && this.joueur2PeutJouer) {
      this.decisionJoueur2 = decision;
    }
  }

  // Avance au tour suivant
  avancerTour() {
    if (this.peutAvancer) {
      console.log('Décisions prises : ', this.decisionJoueur1, this.decisionJoueur2);
      // TODO : Envoyer les décisions au backend ou simuler le prochain tour
      this.decisionJoueur1 = undefined;
      this.decisionJoueur2 = undefined;
    }
  }

  // Obtenir le score total d'un joueur
  getScoreTotal(joueur: 'joueur1' | 'joueur2'): number {
    return this.partie.historique
      .map(tour => (joueur === 'joueur1' ? tour.gainJoueur1 : tour.gainJoueur2))
      .reduce((acc, score) => acc + score, 0);
  }

  // Retourne le tableau historique pour un joueur
  getHistorique(joueur: 'joueur1' | 'joueur2'): (Decision | undefined)[] {
    return this.partie.historique.map(tour =>
      joueur === 'joueur1' ? tour.decisionJoueur1 : tour.decisionJoueur2
    );
  }

  protected readonly Decision = Decision;
}
