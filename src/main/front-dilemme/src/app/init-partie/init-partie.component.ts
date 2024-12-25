import { Component } from '@angular/core';
import { Router } from '@angular/router';

// Importez vos DTO et Enums
import { InitPartieDTO } from '../models/init-partie-dto';
import { TypeStrategie } from '../models/type-strategie';
import { PartieService } from '../services/parties.service';

@Component({
  selector: 'app-init-partie',
  templateUrl: './init-partie.component.html',
  styleUrls: ['./init-partie.component.css']
})
export class InitPartieComponent {

  // Champs liés au formulaire
  nomJoueur1 = '';
  nomJoueur2 = '';
  joueur1bot = false;
  joueur2bot = false;
  strategieJoueur1?: TypeStrategie ;
  strategieJoueur2?: TypeStrategie ;
  strategieExterneJoueur1 = false;
  strategieExterneJoueur2 = false;
  nbMaxTours = 5;
  strategies = Object.values(TypeStrategie);


  constructor(
    private partieService: PartieService,
    private router: Router
  ) {}

  onSubmit() {
    // Construire l'objet DTO
    const dto: InitPartieDTO = {
      nomJoueur1: this.nomJoueur1,
      nomJoueur2: this.nomJoueur2,
      joueur1bot: this.joueur1bot,
      joueur2bot: this.joueur2bot,
      strategieJoueur1: this.strategieJoueur1,
      strategieJoueur2: this.strategieJoueur2,
      strategieExterneJoueur1: this.strategieExterneJoueur1,
      strategieExterneJoueur2: this.strategieExterneJoueur2,
      nbMaxTours: this.nbMaxTours
    };

    // Appel au service pour initier la partie
    this.partieService.demarrerPartie(dto).subscribe({
      next: () => {
        // On suppose que le back renvoie un OutPartieDTO avec par ex. "idPlayer1" ou un id de partie
        // Adaptez la navigation selon ce que vous renvoie votre backend
        // Par exemple, si l'identifiant de la partie est "idPlayer1":
        this.router.navigate(['/play']);
      },
      error: (err) => {
        console.error('Erreur lors de la création de la partie :', err);
      }
    });
  }

}
