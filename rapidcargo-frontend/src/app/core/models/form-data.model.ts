export interface MovementFormData {
  type: 'ENTRY' | 'EXIT';
  awb: string;
  movementTime: string;
}

export interface GoodsFormData {
  description: string;
  weight: number;
  quantity: number;
  value: number;
}

export interface CreateMovementData {
  type: 'ENTRY' | 'EXIT';
  awb: string;
  movementTime: string;
  goods: GoodsFormData;
}
