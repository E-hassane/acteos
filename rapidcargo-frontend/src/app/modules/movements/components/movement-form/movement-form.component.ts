import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MovementService } from '../../../../core/services/movement.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { EntryMovementRequest, ExitMovementRequest, CustomsStatus } from '../../../../core/models/movement.model';
import { Goods } from '../../../../core/models/goods.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-movement-form',
  templateUrl: './movement-form.component.html',
  styleUrls: ['./movement-form.component.scss']
})
export class MovementFormComponent implements OnInit {
  movementForm!: FormGroup;
  currentStep = 1;
  totalSteps = 2;
  selectedMovementType: 'ENTRY' | 'EXIT' = 'ENTRY';
  isSubmitting = false;

  currentGoods: Goods | null = null;
  isGoodsFormValid = false;

  customsStatusOptions = [
    { value: CustomsStatus.X, label: 'X - Transit' },
    { value: CustomsStatus.Y, label: 'Y - Dédouané' },
    { value: CustomsStatus.Z, label: 'Z - Suspendu' }
  ];

  warehouseOptions = [
    { code: 'CDG01', label: 'Entrepôt CDG Terminal 1' },
    { code: 'CDG02', label: 'Entrepôt CDG Terminal 2' },
    { code: 'ORY01', label: 'Entrepôt Orly Sud' },
    { code: 'LBG01', label: 'Entrepôt Le Bourget' }
  ];

  documentTypes = [
    { value: 'T1', label: 'T1 - Transit externe' },
    { value: 'T2', label: 'T2 - Transit interne' },
    { value: 'EXP', label: 'EXP - Exportation' }
  ];

  constructor(
    private formBuilder: FormBuilder,
    private movementService: MovementService,
    private notificationService: NotificationService,
    private loadingService: LoadingService,
    private router: Router
  ) {
    this.createForm();
  }

  ngOnInit(): void {
    const now = new Date();
    const formattedDateTime = now.toISOString().slice(0, 16);
    this.movementForm.patchValue({
      movementTime: formattedDateTime
    });
  }

  private createForm(): void {
    this.movementForm = this.formBuilder.group({
      movementTime: ['', Validators.required],
      customsStatus: [CustomsStatus.X, Validators.required],
      
      fromWarehouseCode: [''],
      fromWarehouseLabel: [''],
      
      toWarehouseCode: [''],
      toWarehouseLabel: [''],
      customsDocumentType: [''],
      customsDocumentRef: ['']
    });

    this.updateValidatorsForMovementType();
  }

  private updateValidatorsForMovementType(): void {
    this.movementForm.get('fromWarehouseCode')?.clearValidators();
    this.movementForm.get('fromWarehouseLabel')?.clearValidators();
    this.movementForm.get('toWarehouseCode')?.clearValidators();
    this.movementForm.get('toWarehouseLabel')?.clearValidators();
    this.movementForm.get('customsDocumentType')?.clearValidators();
    this.movementForm.get('customsDocumentRef')?.clearValidators();

    if (this.selectedMovementType === 'ENTRY') {
      this.movementForm.get('fromWarehouseCode')?.setValidators([Validators.required]);
      this.movementForm.get('fromWarehouseLabel')?.setValidators([Validators.required]);
    } else {
      this.movementForm.get('toWarehouseCode')?.setValidators([Validators.required]);
      this.movementForm.get('toWarehouseLabel')?.setValidators([Validators.required]);
      this.movementForm.get('customsDocumentType')?.setValidators([Validators.required]);
      this.movementForm.get('customsDocumentRef')?.setValidators([Validators.required]);
    }

    this.movementForm.updateValueAndValidity();
  }

  onMovementTypeChange(type: 'ENTRY' | 'EXIT'): void {
    this.selectedMovementType = type;
    this.updateValidatorsForMovementType();
    
    if (type === 'ENTRY') {
      this.movementForm.patchValue({
        toWarehouseCode: '',
        toWarehouseLabel: '',
        customsDocumentType: '',
        customsDocumentRef: ''
      });
    } else {
      this.movementForm.patchValue({
        fromWarehouseCode: '',
        fromWarehouseLabel: ''
      });
    }
  }

  onGoodsChange(goods: Goods): void {
    this.currentGoods = goods;
  }

  onGoodsValidChange(isValid: boolean): void {
    this.isGoodsFormValid = isValid;
  }

  onWarehouseChange(event: Event, type: 'from' | 'to'): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedOption = selectElement.selectedOptions[0];
    const labelText = selectedOption?.text || '';
    
    if (type === 'from') {
      this.movementForm.patchValue({
        fromWarehouseLabel: labelText
      });
    } else {
      this.movementForm.patchValue({
        toWarehouseLabel: labelText
      });
    }
  }

  nextStep(): void {
    if (this.currentStep < this.totalSteps) {
      this.currentStep++;
    }
  }

  previousStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  canProceedToNextStep(): boolean {
    return this.movementForm.valid;
  }

  canSubmit(): boolean {
    return this.movementForm.valid && this.isGoodsFormValid && !!this.currentGoods;
  }

  onSubmit(): void {
    if (!this.canSubmit() || this.isSubmitting) return;

    this.isSubmitting = true;
    this.loadingService.show();

    const formValue = this.movementForm.value;

    if (this.selectedMovementType === 'ENTRY') {
      this.submitEntryMovement(formValue);
    } else {
      this.submitExitMovement(formValue);
    }
  }

  private submitEntryMovement(formValue: any): void {
    const request = new EntryMovementRequest({
      movementTime: formValue.movementTime,
      fromWarehouseCode: formValue.fromWarehouseCode,
      fromWarehouseLabel: formValue.fromWarehouseLabel,
      goods: this.currentGoods!,
      customsStatus: formValue.customsStatus
    });

    this.movementService.createEntry(request).subscribe({
      next: (response) => {
        this.handleSuccess('Mouvement d\'entrée créé avec succès !');
      },
      error: (error) => {
        this.handleError(error);
      }
    });
  }

  private submitExitMovement(formValue: any): void {
    const request = new ExitMovementRequest({
      movementTime: formValue.movementTime,
      toWarehouseCode: formValue.toWarehouseCode,
      toWarehouseLabel: formValue.toWarehouseLabel,
      goods: this.currentGoods!,
      customsStatus: formValue.customsStatus,
      customsDocumentType: formValue.customsDocumentType,
      customsDocumentRef: formValue.customsDocumentRef
    });

    this.movementService.createExit(request).subscribe({
      next: (response) => {
        this.handleSuccess('Mouvement de sortie créé avec succès !');
      },
      error: (error) => {
        this.handleError(error);
      }
    });
  }

  private handleSuccess(message: string): void {
    this.isSubmitting = false;
    this.loadingService.hide();
    this.notificationService.showSuccess(message);
    this.router.navigate(['/movements']);
  }

  private handleError(error: HttpErrorResponse): void {
    this.isSubmitting = false;
    this.loadingService.hide();
    
    let errorMessage = 'Une erreur est survenue lors de la création du mouvement.';
    
    if (error.error?.message) {
      errorMessage = error.error.message;
    }
    
    this.notificationService.showError(errorMessage);
    console.error('Erreur soumission mouvement:', error);
  }

  onCancel(): void {
    this.router.navigate(['/movements']);
  }

  getProgressPercentage(): number {
    return (this.currentStep / this.totalSteps) * 100;
  }

  hasFieldError(fieldName: string): boolean {
    const field = this.movementForm.get(fieldName);
    return !!(field?.invalid && (field.dirty || field.touched));
  }

  getFieldErrorMessage(fieldName: string): string {
    const field = this.movementForm.get(fieldName);
    
    if (!field?.errors) return '';

    if (field.hasError('required')) {
      return 'Ce champ est obligatoire';
    }

    return 'Valeur invalide';
  }
}
