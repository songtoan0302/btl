export interface IProduct {
  id?: number;
  name?: string | null;
  price?: number | null;
  description?: string | null;
  quantity?: number | null;
  urlImage?: string | null;
}

export const defaultValue: Readonly<IProduct> = {};
