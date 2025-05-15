import { OrderSingleProductDto } from "@/app/[lang]/(shared)/checkout/payment/result/PaymentInfo";
import { Country, CountryCode, Locale, SimleSort, SortDirection } from "../common";
import { DeliveryMethod } from "./checkout";

export enum OrderStatus {
  IN_PROGRESS = "IN_PROGRESS",
  PAID = "PAID",
  CANCELLED = "CANCELLED",
  FAILED = "FAILED",
  SHIPPED = "SHIPPED",
  COMPLETED = "COMPLETED",
}

export type OrdersSearchParams = {
  page?: string;
  sort?: SimleSort;
  direction?: SortDirection;
  q?: string;
  status?: OrderStatus;
  country?: CountryCode;
  deliveryMethod?: DeliveryMethod;
};

export type CustomerInfoDto = {
  firstName: string;
  lastName: string;
  email: string;
};

export type DeliveryMethodDto = {
  methodCode: DeliveryMethod;
  methodName: string;
  fullNameAddress: string;
};

export type StatusDto = {
  code: OrderStatus;
  name: string;
};

export type UserMinDto = {
  id: number;
  username: string;
};

export type OrderAdminDto = {
  id: number;
  status: StatusDto;
  total: number;
  customer: CustomerInfoDto;
  delivery: DeliveryMethodDto;
  createdAt: string;
  lastModifiedAt: string | null;
  lastModifiedBy: UserMinDto | null;
};

export type FullDeliveryInfoDto = {
  id: number;
  methodCode: DeliveryMethod;
  methodName: string;
  country: CountryCode;
  price: number;
  deliveryPriceAtOrderTime: number;
};

export type AddressAdminDto = {
  street: string;
  houseNumber: string;
  apartment: string | null;
  city: string;
  postalCode: string;
  country: CountryCode;
};

export type CustomerFullAdminDto = {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  address: AddressAdminDto | null;
  additionalDetails: string | null;
};

export type CouponAdminDto = {
  id: number;
  code: string;
  percentDiscount: number;
  fixedDiscount: number;
  discountAtOrderTime: number;
};

export type OrderFullAdminDto = {
  id: number;
  status: StatusDto;
  deliveryInfo: FullDeliveryInfoDto;
  customerInfo: CustomerFullAdminDto;
  couponInfo: CouponAdminDto | null;
  discountAtOrderTime: number;
  total: number;
  locale: Locale;
  items: OrderSingleProductDto[];
  createdAt: string;
  lastModifiedAt: string | null;
  lastModifiedBy: UserMinDto | null;
};
