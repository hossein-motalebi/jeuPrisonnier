import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PartieService } from '../services/parties.service';

@Component({
  selector: 'app-init-partie',
  templateUrl: './init-partie.component.html',
  styleUrls: ['./init-partie.component.css'],
})
export class InitPartieComponent implements OnInit {
  nomJoueur = '';
  optionSelectionnee: 'nouveau' | 'joindre' | null = null;
  nbMaxTours = 5;
  partiesEnCours: { idPartie: number; adversaire: string }[] = [];
  partieSelectionnee?: number;

  constructor(private partieService: PartieService, private router: Router) {}

  ngOnInit(): void {
    this.actualiserParties();
  }

  actualiserParties(): void {
    this.partieService.obtenirPartiesEnCours().subscribe({
      next: (parties) => {
        this.partiesEnCours = parties;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des parties en cours :', err);
      },
    });
  }

  onSubmit(): void {
    if (this.optionSelectionnee === 'nouveau') {
      this.partieService.demarrerPartie({ nomJoueur: this.nomJoueur, nbMaxTours: this.nbMaxTours });
      this.router.navigate(['/waiting']);
    } else if (this.optionSelectionnee === 'joindre' && this.partieSelectionnee !== undefined) {
      this.partieService.rejoindrePartie(this.partieSelectionnee, this.nomJoueur);
      this.router.navigate(['/waiting']);
    }
  }
}
