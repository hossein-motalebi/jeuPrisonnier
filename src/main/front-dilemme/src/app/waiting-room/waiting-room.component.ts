import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, interval } from 'rxjs';
import { PartieService } from '../services/parties.service';

@Component({
  selector: 'app-waiting-room',
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css'],
})
export class WaitingRoomComponent implements OnInit, OnDestroy {
  message: string = "En attente d'un autre joueur...";
  private partieSubscription?: Subscription;
  private intervalSubscription?: Subscription;

  constructor(private partieService: PartieService, private router: Router) {}

  ngOnInit(): void {
    // Écoute les mises à jour du service
    this.partieSubscription = this.partieService.currentPartie$.subscribe((partie) => {
      if (partie && partie.nomJoueur2 !== 'En attend') {
        this.router.navigate(['/play']); // Redirection lorsque la partie est prête
      }
    });

    // Vérification continue toutes les 2 secondes
    this.intervalSubscription = interval(2000).subscribe(() => {
      this.checkGameStatus();
    });
  }

  ngOnDestroy(): void {
    // Désabonnements pour éviter les fuites mémoire
    this.partieSubscription?.unsubscribe();
    this.intervalSubscription?.unsubscribe();
  }

  checkGameStatus(): void {
    // Vérifie si le jeu est prêt via le service
    const partie = this.partieService.currentPartie$.value;
    if (partie && partie.nomJoueur2 !== 'En attend') {
      this.router.navigate(['/play']); // Redirection si le jeu est prêt
    }
  }
}
