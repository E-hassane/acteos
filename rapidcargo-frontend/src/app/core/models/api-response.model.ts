import { Goods } from './goods.model';

export class MovementResponse {
  id: number = 0;
  movementTime: string = '';
  createdBy: string = '';
  createdAt: string = '';
  type: string = '';
  declarationDate: string = '';
  declaredInCode: string = '';
  declaredInLabel: string = '';
  goods: Goods = new Goods();
  customsStatus: string = '';
  fromWarehouseCode?: string;
  fromWarehouseLabel?: string;
  toWarehouseCode?: string;
  toWarehouseLabel?: string;
  customsDocumentType?: string;
  customsDocumentRef?: string;

  constructor(data: Partial<MovementResponse> = {}) {
    Object.assign(this, data);
    if (data.goods) {
      this.goods = new Goods(data.goods);
    }
  }
}

export class MovementListResponse {
  movements: MovementResponse[] = [];
  total: number = 0;

  constructor(data: Partial<MovementListResponse> = {}) {
    this.movements = data.movements?.map(m => new MovementResponse(m)) || [];
    this.total = data.total || 0;
  }
}

export class ApiErrorResponse {
  error: string = '';
  message: string = '';
  timestamp: string = '';
  status?: number;

  constructor(data: Partial<ApiErrorResponse> = {}) {
    Object.assign(this, data);
  }
}
