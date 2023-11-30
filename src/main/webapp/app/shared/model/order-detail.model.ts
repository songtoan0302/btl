export interface IOrderDetail {
  id?: number;
  recipientName?: string | null;
  receivePhoneNumber?: string | null;
  receiveAddress?: string | null;
  statusPayment?: string | null;
  statusOrder?: string | null;
  paymentMethod?: string | null;
  userId?: number | null;
  ProductByCartId?: object | null;
}

export const defaultValue: Readonly<IOrderDetail> = {};
