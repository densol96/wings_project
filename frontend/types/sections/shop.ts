import { Image, SortDirection } from "@/types";

export type ProductSort = "price" | "createdAt";

export type ProductSearchParams = {
  page: string | number;
  sort: ProductSort;
  direction: SortDirection;
};

export type SortOption = {
  label: string;
  value: string;
};

export type SelectOptions = {
  label: string;
  options: SortOption[];
};

export type ShopDict = {
  meta: string;
  title: string;
  description: string;
  toHome: string;
  categoryListTitle: string;
  select: SelectOptions;
  noProductsMessage: string;
  addToCartBtn: string;
};

export type ShortProductTranslationDto = {
  title: string;
};

export type ShortProductDto = {
  id: number;
  price: number;
  amount: number;
  images: Image[];
  translation: ShortProductTranslationDto;
};
