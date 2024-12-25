import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';
import { InitPartieDTO } from '../models/init-partie-dto';
import { OutPartieDTO } from '../models/out-partie-dto';
import { DecisionDTO } from '../models/decision-dto';
import { AbandonDTO } from '../models/abandon-dto';
import { Observable , tap , BehaviorSubject , ReplaySubject} from 'rxjs';
import { TypeStrategie } from '../models/type-strategie';

@Injectable({
  providedIn: 'root'
})
export class PartieService {
  currentPartie?: OutPartieDTO;
  //strategieJoueur1?: TypeStrategie;
  //strategieJoueur2?: TypeStrategie;

  private readonly baseUrl = environment.apiUrl ;

  private partieInitialiseeSource = new BehaviorSubject<boolean>(false);
  partieInitialisee$ = this.partieInitialiseeSource.asObservable();

  strategiesSubject = new ReplaySubject<{strategieJoueur1 ?: TypeStrategie,
                                    strategieJoueur2 ?:TypeStrategie}>();


  constructor(private readonly http: HttpClient) {}


  /**
   * Appel POST sur /jeu/demarrer
   * - Reçoit un InitPartieDTO en paramètre
   * - Retourne un OutPartieDTO
   */
  demarrerPartie(initDto: InitPartieDTO): Observable<OutPartieDTO> {
    return this.http.post<OutPartieDTO>(
      `${this.baseUrl}/jeu/demarrer`,
      initDto
    ).pipe(
      tap((res) =>{
        this.currentPartie = res;
        this.strategiesSubject.next({strategieJoueur1 : initDto.strategieJoueur1,
                                    strategieJoueur2 : initDto.strategieJoueur2});
      })
    );
  }

  /**
   * Appel POST sur /jeu/jouer-tour
   * - Reçoit un DecisionDTO en paramètre
   * - Retourne un OutPartieDTO
   */
  jouerTour(decisionDto: DecisionDTO): Observable<OutPartieDTO> {
    return this.http.post<OutPartieDTO>(
      `${this.baseUrl}/jeu/jouer-tour`,
      decisionDto
    ).pipe(
      tap((res) =>{
        this.currentPartie = res;
      })
    );
  }

  /**
   * Appel POST sur /jeu/abandonner-humain
   * - Reçoit un AbandonDTO en paramètre
   * - Retourne un OutPartieDTO
   */
  abandonner(abandonDto : AbandonDTO) : Observable<OutPartieDTO>{
    return this.http.post<OutPartieDTO>(
      `${this.baseUrl}/jeu/abandonner-humain`,
      abandonDto
    ).pipe(
      tap((res) => {
          this.currentPartie = res;
          console.log(abandonDto);
        if (abandonDto.idPlayer === 1)
            this.strategiesSubject.next({strategieJoueur1 : abandonDto.strategie});
        else
            this.strategiesSubject.next({strategieJoueur2 : abandonDto.strategie});

      })
    );
  }

}
