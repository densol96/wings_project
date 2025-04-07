import { ImageDto, PagePropsWithSlug, SortDirection } from "@/types";

export type ProductSort = "price" | "createdAt";

export type ProductSearchParams = {
  page: string | number;
  sort: ProductSort;
  direction: SortDirection;
};

export type ProductsPageProps = PagePropsWithSlug & {
  searchParams: ProductSearchParams;
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
  categories: {
    title: string;
    shortTitle: string;
    footerTitle: string;
  };
};

export type ShortProductTranslationDto = {
  title: string;
};

export type ProductTranslationDto = ShortProductTranslationDto & {
  description: string;
};

export type ShortProductCategoryDto = {
  id: number;
  title: string;
};

export type ColorDto = {
  id: number;
  name: string;
};

export type MaterialDto = ColorDto;

export type ProductMaterialDto = {
  material: MaterialDto;
  percentage: number;
};

export type ProductDto = {
  id: number;
  price: number;
  amount: number;
  translationDto: ProductTranslationDto;
  categoryDto: ShortProductCategoryDto;
  imageDtos: ImageDto[];
  colorDtos: ColorDto[];
  materialDtos: ProductMaterialDto[];
};

export type ShortProductDto = {
  id: number;
  price: number;
  amount: number;
  imageDtos: ImageDto[];
  translationDto: ShortProductTranslationDto;
};

export type RandomProductDto = {
  id: number;
  price: number;
  amount: number;
  translationDto: ProductTranslationDto;
  categoryDto: ShortProductCategoryDto;
  imageDtos: ImageDto[];
};

export type CategoryLi = {
  id: number | null;
  title: string;
  productsTotal: number;
};

export type CategoriesDict = {
  title: string;
  footerTitle: string;
};

export type ProductDict = {
  toHome: string;
  isAvailable: string;
  isNotAvailable: string;
  addToCart: string;
  additionalInformation: string;
  color: string;
  material: string;
  invalidAmount: string;
  relatedProducts: string;
};
