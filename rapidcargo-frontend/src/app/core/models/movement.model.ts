/**
 * Énumération des types de référence pour les marchandises
 */
export enum ReferenceType {
  AWB = 'AWB',
  OTHER = 'OTHER'
}

/**
 * Énumération des types de mouvement
 */
export enum MovementType {
  ENTRY = 'ENTRY',
  EXIT = 'EXIT'
}

/**
 * Énumération des statuts douaniers
 */
export enum CustomsStatus {
  X = 'X',
  Y = 'Y',
  Z = 'Z'
}

/**
 * Interface pour les informations de marchandise
 */
export interface Goods {
  referenceType: ReferenceType;
  referenceCode: string;
  quantity: number;
  weight: number;
  totalRefQuantity: number;
  totalRefWeight: number;
  description?: string;
}

/**
 * Interface de base pour tous les mouvements
 */
export interface BaseMovement {
  id?: number;
  movementTime: string; // Format ISO string
  createdBy?: string;
  createdAt?: string;
  declaredInCode?: string;
  declaredInLabel?: string;
  goods: Goods;
  customsStatus: CustomsStatus;
}

/**
 * Interface pour un mouvement d'entrée
 */
export interface EntryMovement extends BaseMovement {
  type: MovementType.ENTRY;
  fromWarehouseCode: string;
  fromWarehouseLabel: string;
}

/**
 * Interface pour un mouvement de sortie
 */
export interface ExitMovement extends BaseMovement {
  type: MovementType.EXIT;
  toWarehouseCode: string;
  toWarehouseLabel: string;
  customsDocumentType: string;
  customsDocumentRef: string;
}

/**
 * Union type pour tous les mouvements
 */
export type Movement = EntryMovement | ExitMovement;

/**
 * Interface pour les données de création d'un mouvement d'entrée
 */
export interface EntryMovementRequest {
  movementTime: string;
  fromWarehouseCode: string;
  fromWarehouseLabel: string;
  goods: Goods;
  customsStatus: CustomsStatus;
}

/**
 * Interface pour les données de création d'un mouvement de sortie
 */
export interface ExitMovementRequest {
  movementTime: string;
  toWarehouseCode: string;
  toWarehouseLabel: string;
  goods: Goods;
  customsStatus: CustomsStatus;
  customsDocumentType: string;
  customsDocumentRef: string;
}
