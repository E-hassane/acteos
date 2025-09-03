import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Notification {
  message: string;
  type: 'success' | 'error' | 'warning' | 'info';
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new BehaviorSubject<Notification | null>(null);
  notification$ = this.notificationSubject.asObservable();

  showSuccess(message: string) {
    this.notificationSubject.next({ message, type: 'success' });
  }

  showError(message: string) {
    this.notificationSubject.next({ message, type: 'error' });
  }

  showWarning(message: string) {
    this.notificationSubject.next({ message, type: 'warning' });
  }

  showInfo(message: string) {
    this.notificationSubject.next({ message, type: 'info' });
  }

  clear() {
    this.notificationSubject.next(null);
  }
}
