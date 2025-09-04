import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/movements',
    pathMatch: 'full'
  },
  {
    path: 'movements',
    loadChildren: () => import('./modules/movements/movements.module').then(m => m.MovementsModule)
  },
  {
    path: '**',
    redirectTo: '/movements'
  }
];
