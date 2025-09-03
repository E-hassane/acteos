import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EntryMovementRequest, ExitMovementRequest } from '../models/movement.model';
import { MovementResponse, MovementListResponse } from '../models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class MovementService {
  private apiUrl = `${environment.apiUrl}/movements`;

  constructor(private http: HttpClient) {}

  getLatestMovements(): Observable<MovementListResponse> {
    return this.http.get<MovementListResponse>(`${this.apiUrl}/latest`);
  }

  createEntry(request: EntryMovementRequest): Observable<MovementResponse> {
    return this.http.post<MovementResponse>(`${this.apiUrl}/entry`, request);
  }

  createExit(request: ExitMovementRequest): Observable<MovementResponse> {
    return this.http.post<MovementResponse>(`${this.apiUrl}/exit`, request);
  }

  getMovementById(id: number): Observable<MovementResponse> {
    return this.http.get<MovementResponse>(`${this.apiUrl}/${id}`);
  }
}
