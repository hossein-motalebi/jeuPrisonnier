import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// On importe nos composants
import { InitPartieComponent } from './init-partie/init-partie.component';
import { PlayPartieComponent } from './play-partie/play-partie.component';

@NgModule({
  declarations: [
    AppComponent,
    InitPartieComponent,    // <--- on déclare ici
    PlayPartieComponent     // <--- et ici
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }