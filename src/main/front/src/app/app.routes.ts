import { Routes } from '@angular/router';
import { StartPageComponent } from './start-page/start-page.component';
import { JeuPageComponent } from './jeu-page/jeu-page.component';
import { EndPageComponent } from './end-page/end-page.component';

export const routes: Routes = [
  { path: '', redirectTo: '/start', pathMatch: 'full' },
  { path: 'start', component: StartPageComponent },
  { path: 'jeu', component: JeuPageComponent },
  { path: 'end', component: EndPageComponent },
];
