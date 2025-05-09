import { PermissionSearchParams, ProductSearchParams, ProductSort, SortDirection, UserSort, UsersSearchParams, UserStatus } from "@/types";

export const validateValues = <T>(value: T | null, allowedValues: T[], defaultValue: T): T => {
  return value && allowedValues.includes(value) ? value : defaultValue;
};

export const validatePage = (page: string | number) => {
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
