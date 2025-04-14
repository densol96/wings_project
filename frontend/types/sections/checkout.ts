import { Country, CountryCode, ImageDto } from "../common";

export type CartItem = {
  id: number;
  title: string;
  price: number;
  image?: ImageDto;
  quantity: number; // added to the cart
  inStockAmount: number; // total avaialable in stock
};

type SharedDictionary = {
  isAlreadyInCart: string;
  total: string;
  totalTogether: string;
  price: string;
  isAvailable: string;
  isNotAvailable: string;
  delivery: string;
  country: string;
  countryList: Country[];
};

type CartDeliveryDictionary = {
  title: string;
  description: string;
  continueShopping: string;
  clearCart: string;
  proceedWithOrder: string;
  cartTotals: string;
  choosePackomat: string;
  emptyCart: string;
  useCoupon: string;
  applyCoupon: string;
  discount: string;
  enterDescription: string;
  explanation: string;
};

export type CartDeliveryRequiredTranslation = SharedDictionary & CartDeliveryDictionary;

export type PersonalInfoTranslation = {
  title: string;
  description: string;
};

export type PaymentTranslation = {
  title: string;
  description: string;
};

export type CheckoutTranslation = {
  cartDelivery: CartDeliveryDictionary;
  personalInfo: PersonalInfoTranslation;
  payment: PaymentTranslation;
};

export enum DeliveryMethod {
  COURIER = "COURIER",
  PICKUP = "PICKUP",
  PARCEL_MACHINE = "PARCEL_MACHINE",
}

export type DeliveryDto = {
  id: number;
  method: DeliveryMethod;
  title: string;
  description: string;
  price: number;
  country: CountryCode;
};

export type TerminalDto = {
  id: number;
  name: string;
  address: string;
  country: CountryCode;
};

export type Coupon = {
  code: string;
  discount: number;
};
