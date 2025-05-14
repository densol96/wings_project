import {
  CountryCode,
  DeliveryMethod,
  EventsSearchParams,
  PermissionSearchParams,
  ProductSearchParams,
  ProductSort,
  SecurityEventType,
  SimleSort,
  SortDirection,
  UserSort,
  UsersSearchParams,
  UserStatus,
} from "@/types";
import { OrdersSearchParams, OrderStatus } from "@/types/sections/admin";

export const validateValues = <T>(value: T | null | undefined, allowedValues: T[], defaultValue: T): T => {
  return value && allowedValues.includes(value) ? value : defaultValue;
};

export const validatePage = (page?: string | number) => {
  const pageNum = Number(page);
  return pageNum && pageNum > 0 ? pageNum : 1;
};

export const validateProductSearchParams = (searchParams: ProductSearchParams) => {
  const page = validatePage(searchParams.page);
  const sort = validateValues<ProductSort>(searchParams.sort, ["price", "createdAt"], "createdAt");
  const direction = validateValues<SortDirection>(searchParams.direction, ["asc", "desc"], "desc");
  return { page, sort, direction };
};

export const validateUsersSearchParams = (searchParams: UsersSearchParams) => {
  const sort = validateValues<UserSort>(searchParams.sort, ["joinDateTime", "lastActivityDateTime"], "lastActivityDateTime");
  const direction = validateValues<SortDirection>(searchParams.direction, ["asc", "desc"], "desc");
  const status = validateValues<UserStatus>(searchParams.status, ["all", "active", "inactive"], "all");
  return { sort, direction, status };
};

export const validatePermissionSearchParams = (searchParams: PermissionSearchParams) => {
  const raw = searchParams.permissions;
  const options = Array.isArray(raw) ? raw : raw ? [raw] : [];
  const permissionIds = options.map(Number).filter((n) => Number.isInteger(n) && n > 0);
  return { permissionIds };
};

export const validateEventsSearchParams = (searchParams: EventsSearchParams) => {
  const page = validatePage(searchParams.page);
  const type = validateValues<SecurityEventType | "">(searchParams.type, Object.values(SecurityEventType), "");
  return { page: page + "", type: type as string, q: searchParams.q || "" };
};

export const validateOrdersSearchParams = (searchParams: OrdersSearchParams) => {
  const page = validatePage(searchParams.page);
  const sort = validateValues<SimleSort>(searchParams.sort, ["createdAt", "lastModifiedAt"], "createdAt");
  const direction = validateValues<SortDirection>(searchParams.direction, ["asc", "desc"], "desc");
  const status = validateValues<OrderStatus | "">(searchParams.status, Object.values(OrderStatus), "");
  const country = validateValues<CountryCode | "">(searchParams.country, ["EE", "LT", "LV"], "");
  const deliveryMethod = validateValues<DeliveryMethod | "">(searchParams.deliveryMethod, Object.values(DeliveryMethod), "");
  return { page, sort, direction, status, country, deliveryMethod, q: searchParams.q };
};
