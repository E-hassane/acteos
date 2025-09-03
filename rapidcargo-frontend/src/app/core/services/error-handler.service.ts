import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from './notification.service';
import { ApiErrorResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(private notificationService: NotificationService) {}

  handleError(error: HttpErrorResponse) {
    let message = 'Une erreur est survenue';

    if (error.error && typeof error.error === 'object') {
      const apiError = new ApiErrorResponse(error.error);
      message = apiError.message || message;
    } else if (error.message) {
      message = error.message;
    }

    switch (error.status) {
      case 400:
        message = 'Données invalides. Vérifiez votre saisie.';
        break;
      case 404:
        message = 'Ressource non trouvée';
        break;
      case 422:
        break;
      case 500:
        message = 'Erreur serveur. Veuillez réessayer.';
        break;
      case 0:
        message = 'Impossible de contacter le serveur';
        break;
    }

    this.notificationService.showError(message);
    console.error('Erreur API:', error);
  }
}
