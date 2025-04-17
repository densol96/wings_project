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

// export type PersonalInfoTranslation = {
//   title: string;
//   description: string;
// };

// export type PaymentTranslation = {
//   title: string;
//   description: string;
// };

// export type CheckoutTranslation = {
//   cartDelivery: CartDeliveryDictionary;
//   personalInfo: PersonalInfoTranslation;
//   payment: PaymentTranslation;
// };

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

export enum PaymentIntentErrorCode {
  UNKNOWN_ITEMS = "unknown-items",
  UNKNOWN_DELIVERY_PRICE = "unknown-delivery-price",
  UNKNOWN_TERMINAL = "unknown-terminal",
  MISSING_ADDRESS = "missing-address",
  ADDRESS_MISSMATCH = "address-missmatch",
  TOTAL_COSTS = "total-costs",
  STRIPE_SERVICE = "stripe-service",
  PRODUCT_AMOUNT_EXCEEDED = "product-amount-exceeded",
  FORM_FIELD = "form-field",

  CART_PROBLEM = "cart-problem",
  DELIVERY_PROBLEM = "delivery-problem",
  EMPTY_CART = "empty-cart",
  DELIVERY_NOT_SELECTED = "delivery-not-selected",
  UNKNOWN_VALIDATION = "unknown-validation",

  PHONE_MUST_MATCH = "phone-must-match",
  PHONE_INVALID = "phone-invalid",
  PHONE_REQUIRED = "phone-required",
}

// export enum CheckoutStep {
//   CART = "CART",
//   DELIVERY = "DELIVERY",
//   FORM = "FORM",
//   SERVER = "SERVER",
// }

export enum CheckoutStep {
  CART,
  DELIVERY,
  FORM,
  SERVER,
}

export type FormErrors = {
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  "address.street"?: string;
  "address.houseNumber"?: string;
  "address.apartment"?: string;
  "address.city"?: string;
  "address.postalCode"?: string;
  additionalDetails?: string;
};

export type CheckoutErrors = {
  message: string;
  step?: CheckoutStep;
  errorCode?: PaymentIntentErrorCode;
  invalidIds?: number | null;
  maxAmount?: number | null;
  fieldErrors?: FormErrors;
};

export type PersonalInfoFormDictionary = {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  street: string;
  houseNumber: string;
  apartment: string;
  city: string;
  postalCode: string;
  country: string;
  additionalDetails: string;
};

export type PersonalInfoSectionDictionary = {
  title: string;
  description: string;
  form: PersonalInfoFormDictionary;
};

export type OrderSummaryDictionary = {
  yourOrder: string;
  total: string;
  proceedToPayment: string;
  backToCart: string;
  discount: string;
  delivery: string;
};

export type PaymentSectionDictionary = {
  errorTitle: string;
  title: string;
  description: string;
  payNow: string;
  imageDescription: string;
  orderSummary: OrderSummaryDictionary;
};
