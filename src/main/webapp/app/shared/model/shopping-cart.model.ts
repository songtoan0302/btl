import { IProduct } from 'app/shared/model/product.model';

export interface IShoppingCart {
  id?: number;
  quantity?: number | null;
  userId?: number | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IShoppingCart> = {};
