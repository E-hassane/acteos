import { Movement } from './movement.model';

/**
 * Interface pour la réponse d'un mouvement créé ou récupéré
 */
export interface MovementResponse {
  id: number;
  movementTime: string;
  createdBy: string;
  createdAt: string;
  type: string;
  declaredInCode: string;
  declaredInLabel: string;
  goods: {
    referenceType: string;
    referenceCode: string;
    quantity: number;
    weight: number;
    totalRefQuantity: number;
    totalRefWeight: number;
    description?: string;
  };
  customsStatus: string;
  // Propriétés spécifiques selon le type
  fromWarehouseCode?: string;
  fromWarehouseLabel?: string;
  toWarehouseCode?: string;
  toWarehouseLabel?: string;
  customsDocumentType?: string;
  customsDocumentRef?: string;
}

/**
 * Interface pour la réponse de la liste des mouvements
 */
export interface MovementListResponse {
  movements: MovementResponse[];
  total: number;
}

/**
 * Interface pour les réponses d'erreur de l'API
 */
export interface ApiErrorResponse {
  error: string;
  message: string;
  timestamp: string;
  status?: number;
}

/**
 * Interface générique pour les réponses API paginées
 */
export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

/**
 * Interface pour les options de requête (filtres, pagination, etc.)
 */
export interface RequestOptions {
  page?: number;
  size?: number;
  sort?: string;
  direction?: 'asc' | 'desc';
}
