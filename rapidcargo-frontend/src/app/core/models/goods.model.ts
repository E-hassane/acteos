export enum ReferenceType {
  AWB = 'AWB',
  OTHER = 'OTHER'
}

export class Goods {
  referenceType: ReferenceType = ReferenceType.AWB;
  referenceCode: string = '';
  quantity: number = 0;
  weight: number = 0;
  totalRefQuantity: number = 0;
  totalRefWeight: number = 0;
  description?: string;

  constructor(data: Partial<Goods> = {}) {
    Object.assign(this, data);
  }
}
