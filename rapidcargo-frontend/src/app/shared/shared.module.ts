import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { NotificationComponent } from './components/notification/notification.component';
import { PageHeaderComponent } from './components/page-header/page-header.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule
  ],
  declarations: [
    LoadingSpinnerComponent,
    NotificationComponent,
    PageHeaderComponent
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    
    LoadingSpinnerComponent,
    LoadingSpinnerComponent,
    NotificationComponent,
    PageHeaderComponent
  ]
})
export class SharedModule { }
