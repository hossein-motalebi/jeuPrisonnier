import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InitPartieComponent } from './init-partie/init-partie.component';
import { WaitingRoomComponent } from './waiting-room/waiting-room.component';
import { PlayPartieComponent } from './play-partie/play-partie.component';

const routes: Routes = [
  { path: '', redirectTo: '/init', pathMatch: 'full' },
  { path: 'init', component: InitPartieComponent },
  { path: 'waiting', component: WaitingRoomComponent },
  { path: 'play', component: PlayPartieComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
