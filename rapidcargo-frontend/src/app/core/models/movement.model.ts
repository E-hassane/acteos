import { Goods } from './goods.model';
import { environment } from '../../../environments/environment';

export enum MovementType {
  ENTRY = 'ENTRY',
  EXIT = 'EXIT'
}

export enum CustomsStatus {
  X = 'X',
  Y = 'Y',
  Z = 'Z'
}

export class BaseMovement {
  id?: number;
  movementTime: string = '';
  createdBy?: string;
  createdAt?: string;
  declaredInCode: string = 'CDGRC1';
  declaredInLabel: string = 'RapidCargo CDG';
  goods: Goods = new Goods();
  customsStatus: CustomsStatus = CustomsStatus.X;

  constructor(data: Partial<BaseMovement> = {}) {
    Object.assign(this, data);
    if (data.goods) {
      this.goods = new Goods(data.goods);
    }
  }
}

export class EntryMovement extends BaseMovement {
  fromWarehouseCode: string = '';
  fromWarehouseLabel: string = '';

  constructor(data: Partial<EntryMovement> = {}) {
    super(data);
    Object.assign(this, data);
  }
}

export class ExitMovement extends BaseMovement {
  toWarehouseCode: string = '';
  toWarehouseLabel: string = '';
  customsDocumentType: string = '';
  customsDocumentRef: string = '';

  constructor(data: Partial<ExitMovement> = {}) {
    super(data);
    Object.assign(this, data);
  }
}

export type Movement = EntryMovement | ExitMovement;

export class EntryMovementRequest {
  movementTime: string = '';
  fromWarehouseCode: string = '';
  fromWarehouseLabel: string = '';
  goods: Goods = new Goods();
  customsStatus: CustomsStatus = CustomsStatus.X;
  createdBy: string = environment.defaultUser;

  constructor(data: Partial<EntryMovementRequest> = {}) {
    Object.assign(this, data);
    if (data.goods) {
      this.goods = new Goods(data.goods);
    }
  }
}

export class ExitMovementRequest {
  movementTime: string = '';
  toWarehouseCode: string = '';
  toWarehouseLabel: string = '';
  goods: Goods = new Goods();
  customsStatus: CustomsStatus = CustomsStatus.X;
  customsDocumentType: string = '';
  customsDocumentRef: string = '';
  createdBy: string = environment.defaultUser;

  constructor(data: Partial<ExitMovementRequest> = {}) {
    Object.assign(this, data);
    if (data.goods) {
      this.goods = new Goods(data.goods);
    }
  }
}
