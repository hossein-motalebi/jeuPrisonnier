import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PartieService } from '../services/parties.service';

@Component({
  selector: 'app-waiting-room',
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css'],
})
export class WaitingRoomComponent implements OnInit {
  message: string = "En attente d'un autre joueur...";

  constructor(private partieService: PartieService, private router: Router) {}

  ngOnInit(): void {
    this.listenForPartieUpdates();
  }

  listenForPartieUpdates(): void {
    this.partieService.currentPartie$.subscribe((partie) => {
      if (partie && partie.nomJoueur2 !== 'En attend') {
        this.router.navigate(['/play']);
      }
    });
  }
}
