import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Goods, ReferenceType } from '../../../../core/models/goods.model';
import { ValidationService } from '../../../../core/services/validation.service';

@Component({
  selector: 'app-goods-form',
  templateUrl: './goods-form.component.html',
  styleUrls: ['./goods-form.component.scss']
})
export class GoodsFormComponent implements OnInit {
  @Input() initialGoods?: Goods;
  @Output() goodsChange = new EventEmitter<Goods>();
  @Output() validChange = new EventEmitter<boolean>();

  goodsForm!: FormGroup;
  referenceTypes = Object.values(ReferenceType);

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValidationService
  ) {
    this.createForm();
  }

  ngOnInit(): void {
    if (this.initialGoods) {
      this.goodsForm.patchValue(this.initialGoods);
    }

    this.validChange.emit(this.goodsForm.valid);

    this.goodsForm.valueChanges.subscribe(() => {
      this.emitGoodsAndValidity();
    });
  }

  private createForm(): void {
    this.goodsForm = this.formBuilder.group({
      referenceType: [ReferenceType.AWB, Validators.required],
      referenceCode: ['', [
        Validators.required,
        this.validationService.awbFormatValidator()
      ]],
      description: ['', Validators.required],
      quantity: [1, [
        Validators.required,
        Validators.min(1)
      ]],
      weight: [0.01, [
        Validators.required,
        Validators.min(0.01)
      ]],
      totalRefQuantity: [1, [
        Validators.required,
        Validators.min(1)
      ]],
      totalRefWeight: [0.01, [
        Validators.required,
        Validators.min(0.01)
      ]]
    });

    this.goodsForm.get('quantity')?.valueChanges.subscribe(() => {
      this.validateTotals();
    });

    this.goodsForm.get('weight')?.valueChanges.subscribe(() => {
      this.validateTotals();
    });
  }

  private validateTotals(): void {
    const quantity = this.goodsForm.get('quantity')?.value || 0;
    const weight = this.goodsForm.get('weight')?.value || 0;
    const totalQuantity = this.goodsForm.get('totalRefQuantity')?.value || 0;
    const totalWeight = this.goodsForm.get('totalRefWeight')?.value || 0;

    if (totalQuantity < quantity) {
      this.goodsForm.get('totalRefQuantity')?.setValue(quantity, { emitEvent: false });
    }

    if (totalWeight < weight) {
      this.goodsForm.get('totalRefWeight')?.setValue(weight, { emitEvent: false });
    }
  }

  private emitGoodsAndValidity(): void {
    const goods = new Goods(this.goodsForm.value);
    this.goodsChange.emit(goods);
    this.validChange.emit(this.goodsForm.valid);
  }

  hasFieldError(fieldName: string, errorType: string): boolean {
    const field = this.goodsForm.get(fieldName);
    return !!(field?.hasError(errorType) && (field.dirty || field.touched));
  }

  getFieldErrorMessage(fieldName: string): string {
    const field = this.goodsForm.get(fieldName);
    
    if (!field || !field.errors) return '';

    if (field.hasError('required')) {
      return 'Ce champ est obligatoire';
    }

    if (field.hasError('min')) {
      const minValue = field.errors['min'].min;
      return `La valeur doit être supérieure ou égale à ${minValue}`;
    }

    if (field.hasError('awbFormat')) {
      return 'Le code AWB doit contenir exactement 11 chiffres';
    }

    return 'Valeur invalide';
  }

  getReferenceTypeLabel(type: ReferenceType): string {
    return type === ReferenceType.AWB ? 'AWB' : 'Autre';
  }
}
