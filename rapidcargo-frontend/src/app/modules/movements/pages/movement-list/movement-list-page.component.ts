import { Component, OnInit } from '@angular/core';
import { MovementService } from '../../../../core/services/movement.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { MovementResponse } from '../../../../core/models/api-response.model';

@Component({
  selector: 'app-movement-list-page',
  templateUrl: './movement-list-page.component.html',
  styleUrls: ['./movement-list-page.component.scss']
})
export class MovementListPageComponent implements OnInit {
  movements: MovementResponse[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(
    private movementService: MovementService,
    private loadingService: LoadingService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadMovements();
  }

  loadMovements(): void {
    this.isLoading = true;
    this.error = null;
    
    this.movementService.getLatestMovements().subscribe({
      next: (response) => {
        this.movements = response.movements;
        this.isLoading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors du chargement des mouvements';
        this.isLoading = false;
        this.notificationService.showError(this.error);
        console.error('Erreur chargement mouvements:', error);
      }
    });
  }

  onRefresh(): void {
    this.loadMovements();
    this.notificationService.showInfo('Liste des mouvements actualisée');
  }

}
