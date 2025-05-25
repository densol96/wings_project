import { adminFetch } from "@/actions/helpers/adminFetch";
import {
  CategoryLi,
  CategoryUpdateDto,
  ColorDto,
  ExistingCategoryDto,
  ExistingCategoryTranslationDto,
  ExistingProductDto,
  ExistingProductTranslationDto,
  MaterialDto,
  ProductUpdateDto,
} from "@/types";
import { fetcher } from "@/utils";
// colors, categories, materials

export const loadCategories = async () => await fetcher<CategoryLi[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories`);

export const loadColors = async () => await fetcher<ColorDto[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/colors`);

export const loadMaterials = async () => await fetcher<MaterialDto[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/materials`);

export const loadProductsMeta = async () => {
  const [categories, colors, materials] = await Promise.all([loadCategories(), loadColors(), loadMaterials()]);
  return { categories, colors, materials };
};

export const existingProductToForm = (product: ExistingProductDto): ProductUpdateDto => {
  const lvTranslation = product.translations.find((t) => t.locale.toLowerCase() === "lv") as ExistingProductTranslationDto;
  const enTranslation = product.translations.find((t) => t.locale.toLowerCase() === "en") as ExistingProductTranslationDto;

  return {
    id: product.id,
    titleLv: lvTranslation.title,
    titleEn: enTranslation.title,
    descriptionLv: lvTranslation?.description ?? "",
    descriptionEn: enTranslation?.description ?? "",
    price: product.price,
    amount: product.amount,
    categoryId: product.categoryId,
    colorIds: product.colors,
    productMaterials: product.materials.map((m) => ({
      materialId: m.id,
      percentage: m.percentage,
      materialName: m.name,
    })),
  };
};

export const existingCategoryToForm = (category: ExistingCategoryDto): CategoryUpdateDto => {
  const lvTranslation = category.translations.find((t) => t.locale.toLowerCase() === "lv") as ExistingCategoryTranslationDto;
  const enTranslation = category.translations.find((t) => t.locale.toLowerCase() === "en") as ExistingCategoryTranslationDto;

  return {
    id: category.id,
    titleLv: lvTranslation.title,
    titleEn: enTranslation.title,
    descriptionLv: lvTranslation?.description ?? "",
    descriptionEn: enTranslation?.description ?? "",
  };
};
