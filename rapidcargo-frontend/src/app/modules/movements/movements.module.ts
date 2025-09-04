import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { MovementsRoutingModule } from './movements-routing.module';

import { MovementTableComponent } from './components/movement-table/movement-table.component';
import { MovementFormComponent } from './components/movement-form/movement-form.component';
import { GoodsFormComponent } from './components/goods-form/goods-form.component';

import { MovementListPageComponent } from './pages/movement-list/movement-list-page.component';
import { MovementCreatePageComponent } from './pages/movement-create/movement-create-page.component';

@NgModule({
  imports: [
    SharedModule,
    MovementsRoutingModule
  ],
  declarations: [
    MovementTableComponent,
    MovementFormComponent,
    GoodsFormComponent,
    
    MovementListPageComponent,
    MovementCreatePageComponent
  ]
})
export class MovementsModule { }
