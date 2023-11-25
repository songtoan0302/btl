import { IOrderDetail } from 'app/shared/model/order-detail.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IOrderProduct {
  id?: number;
  productName?: string | null;
  quantity?: number | null;
  price?: number | null;
  order?: IOrderDetail | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IOrderProduct> = {};
