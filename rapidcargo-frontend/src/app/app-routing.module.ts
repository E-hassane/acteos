import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/movements',
    pathMatch: 'full'
  },
  {
    path: 'movements',
    loadChildren: () => import('./modules/movements/movements.module').then(m => m.MovementsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
