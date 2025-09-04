import { Component, Input } from '@angular/core';
import { MovementResponse } from '../../../../core/models/api-response.model';

@Component({
  selector: 'app-movement-table',
  templateUrl: './movement-table.component.html',
  styleUrls: ['./movement-table.component.scss']
})
export class MovementTableComponent {
  @Input() movements: MovementResponse[] = [];
  @Input() loading: boolean = false;

  getMovementTypeLabel(type: string): string {
    switch (type) {
      case 'ENTRY': return 'Entrée';
      case 'EXIT': return 'Sortie';
      case 'CONS': return 'Consommation';
      default: return type;
    }
  }

  getMovementTypeClass(type: string): string {
    switch (type) {
      case 'ENTRY': return 'bg-success';
      case 'EXIT': return 'bg-warning text-dark';
      case 'CONS': return 'bg-info text-dark';
      default: return 'bg-secondary';
    }
  }

  formatMovementDate(dateString: string): string {
    if (!dateString) return '-';
    
    return new Date(dateString).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    });
  }

  getOriginDestination(movement: MovementResponse): string {
    if (movement.type === 'ENTRY') {
      return movement.fromWarehouseLabel || movement.fromWarehouseCode || '-';
    } else if (movement.type === 'EXIT') {
      return movement.toWarehouseLabel || movement.toWarehouseCode || '-';
    }
    return '-';
  }

  getOriginWarehouse(movement: MovementResponse): string {
    if (movement.type === 'ENTRY') {
      return movement.fromWarehouseLabel || movement.fromWarehouseCode || '-';
    } else if (movement.type === 'EXIT') {
      return 'CDGRC1 - RapidCargo CDG';
    }
    return '-';
  }

  getDestinationWarehouse(movement: MovementResponse): string {
    if (movement.type === 'ENTRY') {
      return 'CDGRC1 - RapidCargo CDG';
    } else if (movement.type === 'EXIT') {
      return movement.toWarehouseLabel || movement.toWarehouseCode || '-';
    }
    return '-';
  }

  formatReferenceCode(code: string): string {
    if (!code) return '-';
    
    if (code.length === 11 && /^\d{11}$/.test(code)) {
      return `AWB ${code.substring(0, 3)} ${code.substring(3, 7)} ${code.substring(7)}`;
    }
    
    return code;
  }

  formatWeight(weight: number): string {
    if (weight === null || weight === undefined) return '-';
    return `${weight.toFixed(2)} kg`;
  }
}
