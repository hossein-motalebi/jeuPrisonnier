import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { BackendService } from '../backend.service';
import { PartieStateService } from '../partie-state.service';

@Component({
  selector: 'app-start-page',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatIconModule, MatButtonModule, MatInputModule],
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent {
  player1Name = '';
  player2Name = '';
  nbTours = 0;

  constructor(
    private router: Router,
    private backend: BackendService,
    private partieState: PartieStateService
  ) {}

  get canStart(): boolean {
    return this.player1Name.trim().length > 0 &&
      this.player2Name.trim().length > 0 &&
      this.nbTours > 0;
  }


  commencer() {
    if (this.canStart) {
      this.backend.demarrerPartie(this.player1Name, this.player2Name, this.nbTours)
        .subscribe(partie => {
          this.partieState.setPartie(partie);
          this.router.navigate(['/jeu']);
        });
    }
  }
}
