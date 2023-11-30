export interface IProduct {
  id?: number;
  name?: string | null;
  price?: number | null;
  description?: string | null;
  quantity?: number | null;
  urlImage?: string | null;
  product?: {
    id?:string
  }
}

export const defaultValue: Readonly<IProduct> = {};
