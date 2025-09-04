import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { LoadingService } from '../../../core/services/loading.service';

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.scss']
})
export class LoadingSpinnerComponent {
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() message: string = 'Chargement...';
  @Input() show: boolean = true;
  
  loading$: Observable<boolean>;

  constructor(private loadingService: LoadingService) {
    this.loading$ = this.loadingService.loading$;
  }

  getSizeClass(): string {
    switch (this.size) {
      case 'sm': return 'spinner-border-sm';
      case 'lg': return '';
      default: return '';
    }
  }
}
