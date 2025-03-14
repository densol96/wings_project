export type ProductSortType = "cenaAsc" | "cenaDesc" | "popular" | "default";

export type ProductSearchParams = {
  page?: string | number;
  sortBy: ProductSortType;
};
