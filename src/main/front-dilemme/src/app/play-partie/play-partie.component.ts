import { Component, OnInit } from '@angular/core';
import { PartieService } from '../services/parties.service';
import { OutPartieDTO } from '../models/out-partie-dto';

@Component({
  selector: 'app-play-partie',
  templateUrl: './play-partie.component.html',
  styleUrls: ['./play-partie.component.css'],
})
export class PlayPartieComponent implements OnInit {
  partieStatus?: OutPartieDTO;
  decision: 'COOPERER' | 'TRAHIR' | null = null;
  waitingForOtherPlayer: boolean = false;

  constructor(private partieService: PartieService) {}

  ngOnInit(): void {
    this.listenForUpdates();
  }

  listenForUpdates(): void {
    this.partieService.currentPartie$.subscribe((partie) => {
      this.partieStatus = partie || undefined;
      this.waitingForOtherPlayer = false;
    });
  }

  onJouerCoup(): void {
    if (!this.decision) return;

    this.waitingForOtherPlayer = true;
    const decisionDto = { decisionJoueur1: this.decision, decisionJoueur2: null };

    this.partieService.jouerTour(decisionDto);
  }
}
