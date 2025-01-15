import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// On importe nos composants
import { InitPartieComponent } from './init-partie/init-partie.component';
import { PlayPartieComponent } from './play-partie/play-partie.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import { WaitingRoomComponent } from './waiting-room/waiting-room.component';

@NgModule({
  declarations: [
    AppComponent,
    InitPartieComponent,    // <--- on déclare ici
    PlayPartieComponent, WaitingRoomComponent     // <--- et ici
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
