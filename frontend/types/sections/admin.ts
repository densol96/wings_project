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

export type ProductsSearchParams = {
  page?: string;
  sort?: SimleSort;
  direction?: SortDirection;
  q?: string;
  categoryId?: number;
};

export type ProductAdminDto = {
  id: number;
  amount: number;
  sold: number;
  translations: TitleLocalableDto[];
  createdBy: UserMinDto;
  createdAt: string;
  lastModifiedBy?: UserMinDto;
  lastModifiedAt?: string;
};

export type TitleLocalableDto = {
  locale: string;
  title: string;
};

export type ColorDto = {
  id: number;
  name: string;
};

export type ProductMaterialComboDto = {
  materialId: number;
  materialName: string;
  percentage: number;
  // productId will go and come in the context of the currently viewed product
};

export type ProductUpdateDto = {
  id: number;
  titleLv: string;
  titleEn: string;
  descriptionLv: string;
  descriptionEn: string;
  price: number;
  amount: number;
  categoryId: number;
  colorIds: number[];
  productMaterials: ProductMaterialComboDto[];
};

export type ExistingProductTranslationDto = {
  locale: Locale;
  title: string;
  description?: string;
};

export type ExistingProductMaterialDto = {
  id: number;
  percentage: number;
  name: string;
};

export type ExistingProductDto = {
  id: number;
  price: number;
  amount: number;
  categoryId: number;
  translations: ExistingProductTranslationDto[];
  colors: number[];
  materials: ExistingProductMaterialDto[];
};

export interface AdminImageDto {
  id: number;
  src: string;
  alt: string;
  createdBy: UserMinDto;
  createdAt: string;
}

export type ProductCategoriesSearchParams = {
  sort?: SimleSort;
  direction?: SortDirection;
};

export interface AdminProductCategoryDto {
  id: number;
  translations: TitleLocalableDto[];
  createdBy: UserMinDto;
  createdAt: string;
  lastModifiedBy: UserMinDto | null;
  lastModifiedAt: string | null;
  productsTotal: number;
}

export type CategoryUpdateDto = {
  id: number;
  titleLv: string;
  titleEn: string;
  descriptionLv: string;
  descriptionEn: string;
};

export type ExistingCategoryTranslationDto = {
  locale: Locale;
  title: string;
  description?: string;
};

export type ExistingCategoryDto = {
  id: number;
  translations: ExistingCategoryTranslationDto[];
};

export type AdminEventsSearchParams = {
  page?: string;
  sort?: SimleSort;
  direction?: SortDirection;
  q?: string;
};

export type ExistingEventTranslationDto = {
  locale: Locale;
  title: string;
  description: string;
  location: string;
};

export type ExistingEventDto = {
  id: number;
  translations: ExistingEventTranslationDto[];
  startDate: string;
  endDate: string;
};

export type EventUpdateDto = {
  id: number;
  titleLv: string;
  titleEn: string;
  descriptionLv: string;
  descriptionEn: string;
  locationLv: string;
  locationEn: string;
  startDate: string;
  endDate: string;
};
