import { ProductSearchParams, ProductSort, SortDirection } from "@/types";

export const validateValues = <T>(value: T, allowedValues: T[], defaultValue: T): T => {
  return allowedValues.includes(value) ? value : defaultValue;
};

export const validateSearchParams = (searchParams: ProductSearchParams) => {
  const pageNum = Number(searchParams.page);
  const page = pageNum && pageNum > 0 ? pageNum : 1;
  const sort = validateValues<ProductSort>(searchParams.sort, ["price", "createdAt"], "createdAt");
  const direction = validateValues<SortDirection>(searchParams.direction, ["asc", "desc"], "desc");
  return { page, sort, direction };
};
