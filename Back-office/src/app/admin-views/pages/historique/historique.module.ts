import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HistoriqueRoutingModule } from './historique-routing.module';
import { HistoriqueComponent } from './historique.component';


@NgModule({
  declarations: [
    HistoriqueComponent
  ],
  imports: [
    CommonModule,
    HistoriqueRoutingModule
  ]
})
export class HistoriqueModule { }
