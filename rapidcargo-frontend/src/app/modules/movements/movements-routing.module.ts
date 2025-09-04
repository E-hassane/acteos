import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovementListPageComponent } from './pages/movement-list/movement-list-page.component';
import { MovementCreatePageComponent } from './pages/movement-create/movement-create-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    component: MovementListPageComponent,
    data: { title: 'Liste des mouvements' }
  },
  {
    path: 'create',
    component: MovementCreatePageComponent,
    data: { title: 'Créer un mouvement' }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovementsRoutingModule { }
