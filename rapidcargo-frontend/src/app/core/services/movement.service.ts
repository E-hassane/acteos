import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EntryMovementRequest, ExitMovementRequest, CustomsStatus } from '../models/movement.model';
import { MovementResponse, MovementListResponse } from '../models/api-response.model';
import { CreateMovementData, GoodsFormData } from '../models/form-data.model';
import { Goods, ReferenceType } from '../models/goods.model';

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

  private createGoodsFromFormData(formData: GoodsFormData, awbCode: string): Goods {
    return new Goods({
      referenceType: ReferenceType.AWB,
      referenceCode: awbCode,
      description: formData.description,
      weight: formData.weight,
      quantity: formData.quantity,
      totalRefQuantity: formData.quantity,
      totalRefWeight: formData.weight
    });
  }

  createMovement(movementData: CreateMovementData): Observable<MovementResponse> {
    const goods = this.createGoodsFromFormData(movementData.goods, movementData.awb);
    
    if (movementData.type === 'ENTRY') {
      const entryRequest: EntryMovementRequest = {
        movementTime: movementData.movementTime,
        fromWarehouseCode: 'CDGRC1',
        fromWarehouseLabel: 'RapidCargo CDG',
        goods: goods,
        customsStatus: CustomsStatus.X,
        createdBy: environment.defaultUser
      };
      return this.createEntry(entryRequest);
    } else {
      const exitRequest: ExitMovementRequest = {
        movementTime: movementData.movementTime,
        toWarehouseCode: 'CDGRC1',
        toWarehouseLabel: 'RapidCargo CDG',
        goods: goods,
        customsStatus: CustomsStatus.X,
        customsDocumentType: 'CUSTOMS_DOC',
        customsDocumentRef: movementData.awb,
        createdBy: environment.defaultUser
      };
      return this.createExit(exitRequest);
    }
  }

  getMovementById(id: number): Observable<MovementResponse> {
    return this.http.get<MovementResponse>(`${this.apiUrl}/${id}`);
  }
}
