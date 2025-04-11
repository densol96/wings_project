import { ImageDto } from "../common";

export type CartItem = {
  id: number;
  title: string;
  price: number;
  image?: ImageDto;
  quantity: number; // added to the cart
  inStockAmount: number; // total avaialable in stock
};

export type SharedTranslation = {
  total: string;
  price: string;
  isAvailable: string;
};

export type CartDeliveryTranslation = {
  title: string;
  description: string;
  clearCart: string;
  continueShopping: string;
  proceedWithOrder: string;
  cartSummary: string;
  deliveryType: string;
  choosePackomat: string;
  emptyCart: string;
};

export type CartDeliveryRequiredTranslation = SharedTranslation & CartDeliveryTranslation;

export type PersonalInfoTranslation = {
  title: string;
  description: string;
};

export type PaymentTranslation = {
  title: string;
  description: string;
};

export type CheckoutTranslation = {
  cartDelivery: CartDeliveryTranslation;
  personalInfo: PersonalInfoTranslation;
  payment: PaymentTranslation;
};
