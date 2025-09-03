import { Injectable } from '@angular/core';
import { Goods, ReferenceType } from '../models/goods.model';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  validateAwbCode(referenceType: ReferenceType, referenceCode: string): string | null {
    if (referenceType === ReferenceType.AWB) {
      if (!/^\d{11}$/.test(referenceCode)) {
        return 'Le code AWB doit contenir exactement 11 chiffres';
      }
    }
    return null;
  }

  validateQuantityConsistency(quantity: number, totalQuantity: number): string | null {
    if (quantity > totalQuantity) {
      return 'La quantité ne peut pas dépasser la quantité totale';
    }
    return null;
  }

  validateWeightConsistency(weight: number, totalWeight: number): string | null {
    if (weight > totalWeight) {
      return 'Le poids ne peut pas dépasser le poids total';
    }
    return null;
  }

  validateGoods(goods: Goods): string[] {
    const errors: string[] = [];

    if (!goods.referenceCode.trim()) {
      errors.push('Le code de référence est obligatoire');
    }

    const awbError = this.validateAwbCode(goods.referenceType, goods.referenceCode);
    if (awbError) {
      errors.push(awbError);
    }

    if (goods.quantity <= 0) {
      errors.push('La quantité doit être positive');
    }

    if (goods.weight <= 0) {
      errors.push('Le poids doit être positif');
    }

    const quantityError = this.validateQuantityConsistency(goods.quantity, goods.totalRefQuantity);
    if (quantityError) {
      errors.push(quantityError);
    }

    const weightError = this.validateWeightConsistency(goods.weight, goods.totalRefWeight);
    if (weightError) {
      errors.push(weightError);
    }

    return errors;
  }
}
