import { Component, OnInit } from '@angular/core';
import { PartieService } from '../services/parties.service';
import { OutPartieDTO } from '../models/out-partie-dto';
import { DecisionDTO } from '../models/decision-dto';
import { TypeStrategie } from '../models/type-strategie';

@Component({
  selector: 'app-play-partie',
  templateUrl: './play-partie.component.html',
  styleUrls: ['./play-partie.component.css'],
})
export class PlayPartieComponent implements OnInit {
  partieStatus?: OutPartieDTO;
  decision: 'COOPERER' | 'TRAHIR' | null = null;
  waitingForOtherPlayer: boolean = false;
  idPlayer: number;
  hasMadeDecision: boolean = false;
  hasAbandoned: boolean = false;

  
  constructor(private partieService: PartieService) {
    this.idPlayer = this.partieService.idJoueur ??0;
    if (this.idPlayer === undefined) {
    throw new Error('idJoueur is undefined');
    }
  }

    // Variables pour l'abandon
    showAbandonOptionsJ1: boolean = false;
    showAbandonOptionsJ2: boolean = false;
    selectedStrategieJ1: TypeStrategie | null = null;
    selectedStrategieJ2: TypeStrategie | null = null;
    strategieExterneJ1: boolean = false;
    strategieExterneJ2: boolean = false;
    strategies = Object.values(TypeStrategie);

  ngOnInit(): void {
    this.listenForUpdates();
  }

  listenForUpdates(): void {
    this.partieService.currentPartie$.subscribe((partie) => {
      this.partieStatus = partie || undefined;
      this.waitingForOtherPlayer = false;
      this.hasMadeDecision = false;
      this.decision = null;
    });
  }

  onJouerCoup(): void {
    if (!this.decision) return;

    this.hasMadeDecision = true;
    this.waitingForOtherPlayer = true;
    const decisionDto : DecisionDTO = { idJoueur : this.idPlayer , decision: this.decision };
    this.partieService.jouerTour(decisionDto);
  }

  onAbandonnerJoueur() {
    if (!this.partieStatus) return;
    if (this.idPlayer=== 1 && !this.partieStatus.joueur1bot) {
      this.showAbandonOptionsJ1 = true;
    }
    else if (this.idPlayer === 2 && !this.partieStatus.joueur2bot) {
      this.showAbandonOptionsJ2 = true;
    }
  }

  approuverAbandon() {
    if (!this.partieStatus) return;
    let abandonDto: any = {
      idPlayer: this.idPlayer,
      strategie: this.idPlayer === 1 ? this.selectedStrategieJ1 : this.selectedStrategieJ2,
      strategieExterne: this.idPlayer === 1 ? this.strategieExterneJ1 : this.strategieExterneJ2
    };
    this.hasAbandoned = true;
    this.partieService.abandonner(abandonDto);
    this.annulerAbandon();
  }

  annulerAbandon() {
    if (this.idPlayer === 1) {
      this.showAbandonOptionsJ1 = false;
      this.selectedStrategieJ1 = null;
      this.strategieExterneJ1 = false;
    } else {
      this.showAbandonOptionsJ2 = false;
      this.selectedStrategieJ2 = null;
      this.strategieExterneJ2 = false;
    }
  }
}
