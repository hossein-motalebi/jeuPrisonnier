// src/app/play-partie/play-partie.component.ts
import { Component, OnInit } from '@angular/core';
import { PartieService } from '../services/parties.service';

// Import de vos modèles
import { OutPartieDTO } from '../models/out-partie-dto';
import { DecisionDTO } from '../models/decision-dto';
import { TypeStrategie } from '../models/type-strategie'; // [`TypeStrategie`](src/app/models/type-strategie.ts)

@Component({
  selector: 'app-play-partie',
  templateUrl: './play-partie.component.html',
  styleUrls: ['./play-partie.component.css']
})
export class PlayPartieComponent implements OnInit {

  // Reçu depuis la navigation ou injecté par un service
  partieStatus?: OutPartieDTO;
  strategieJoueur1?: TypeStrategie | null;
  strategieJoueur2?: TypeStrategie | null;

  // Décisions par défaut
  decisionJoueur1: 'COOPERER' | 'TRAHIR' | null = null;
  decisionJoueur2: 'COOPERER' | 'TRAHIR' | null = null;

  // Variables pour l'abandon
  showAbandonOptionsJ1: boolean = false;
  showAbandonOptionsJ2: boolean = false;
  selectedStrategieJ1: TypeStrategie | null = null;
  selectedStrategieJ2: TypeStrategie | null = null;
  strategieExterneJ1: boolean = false;
  strategieExterneJ2: boolean = false;
  strategies = Object.values(TypeStrategie);



  constructor(private partieService: PartieService) {}

  ngOnInit(): void {

    this.partieStatus = this.partieService.currentPartie;
    this.partieService.strategiesSubject.subscribe(strategieObj => {
        if (strategieObj.strategieJoueur1)
          this.strategieJoueur1 = strategieObj.strategieJoueur1;
        if (strategieObj.strategieJoueur2)
          this.strategieJoueur2 = strategieObj.strategieJoueur2;
      });

    if (this.partieStatus) {
    }
  }

  onJouerCoup() {
    if (!this.partieStatus) {
      return;
    }

    const decision1 = this.partieStatus.joueur1bot ? null : this.decisionJoueur1;
    const decision2 = this.partieStatus.joueur2bot ? null : this.decisionJoueur2;

    const decisionDto: DecisionDTO = {
      decisionJoueur1: decision1,
      decisionJoueur2: decision2
    };

    this.partieService.jouerTour(decisionDto).subscribe({
      next: (newStatus) => {
        this.partieStatus = newStatus;
      },
      error: (err) => {
        console.error('Erreur lors de l\'envoi de la décision :', err);
      }
    });
  }

  onAbandonnerJoueur(joueur: number) {
    if (!this.partieStatus) return;
    if (joueur === 1 && !this.partieStatus.joueur1bot) {
      this.showAbandonOptionsJ1 = true;
    }
    if (joueur === 2 && !this.partieStatus.joueur2bot) {
      this.showAbandonOptionsJ2 = true;
    }
  }

  approuverAbandon(joueur: number) {
    if (!this.partieStatus) return;

    let abandonDto: any = {
      idPlayer: joueur === 1 ? this.partieStatus.idPlayer1 : this.partieStatus.idPlayer2,
      strategie: joueur === 1 ? this.selectedStrategieJ1 : this.selectedStrategieJ2,
      strategieExterne: joueur === 1 ? this.strategieExterneJ1 : this.strategieExterneJ2
    };

    this.partieService.abandonner(abandonDto).subscribe({
      next: (newStatus) => {
        this.partieStatus = newStatus;
        if (joueur === 1) {
          this.showAbandonOptionsJ1 = false;
          this.selectedStrategieJ1 = null;
          this.strategieExterneJ1 = false;
        } else {
          this.showAbandonOptionsJ2 = false;
          this.selectedStrategieJ2 = null;
          this.strategieExterneJ2 = false;
        }
      },
      error: (err) => {
        console.error('Erreur lors de l\'abandon :', err);
      }
    });
  }

  annulerAbandon(joueur: number) {
    if (joueur === 1) {
      this.showAbandonOptionsJ1 = false;
      this.selectedStrategieJ1 = null;
      this.strategieExterneJ1 = false;
    } else {
      this.showAbandonOptionsJ2 = false;
      this.selectedStrategieJ2 = null;
      this.strategieExterneJ2 = false;
    }
  }

  // Getter pour vérifier si les décisions sont valides
  get peutJouer(): boolean {
    if (!this.partieStatus) return false;
    const decision1Valide = this.partieStatus.joueur1bot || this.decisionJoueur1 !== null;
    const decision2Valide = this.partieStatus.joueur2bot || this.decisionJoueur2 !== null;
    return decision1Valide && decision2Valide;
  }
}
