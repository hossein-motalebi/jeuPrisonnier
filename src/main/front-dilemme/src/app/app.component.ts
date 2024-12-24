// src/main/front-dilemme/src/app/app.component.ts

import { Component } from '@angular/core';
import { PartieService } from './services/parties.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  partieInitialisee: boolean = false;

  constructor(private partieService: PartieService) {}

  ngOnInit(): void {
    this.partieService.partieInitialisee$.subscribe((status) => {
      this.partieInitialisee = status;
    });
  }
}