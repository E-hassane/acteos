import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { MovementsRoutingModule } from './movements-routing.module';

@NgModule({
  imports: [
    SharedModule,
    MovementsRoutingModule
  ],
  declarations: []
})
export class MovementsModule { }
