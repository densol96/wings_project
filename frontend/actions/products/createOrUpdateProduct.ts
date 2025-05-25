"use server";

import { Locale, MultiLangFormState, TranslationMethod } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { defaultLocale, localeCodes } from "@/constants/locales";
import { revalidatePath } from "next/cache";
import { cleanFormData } from "@/utils";

export const createOrUpdateProduct = async (formData: FormData, productId?: number): Promise<MultiLangFormState> => {
  /*
   * Most of the required info is already inside the formData: amount, price, categoryId, colors, product materisla etc.
   * But some need to be formatted (like title-lv and description-en)
   * as well some need to be cleared out images-input (since images were added from the state)
   */

  let i = 0;
  localeCodes.forEach((localeCode) => {
    if (localeCode === defaultLocale || formData.get("translationMethod") === TranslationMethod.MANUAL) {
      const titleKey = `title-${localeCode}`;
      const descriptionKey = `description-${localeCode}`;

      const description = formData.get(descriptionKey);

      formData.append(`translations[${i}].locale`, localeCode);
      formData.append(`translations[${i}].title`, formData.get(titleKey) || "");
      if (description) {
        formData.append(`translations[${i}].description`, description);
      }
      formData.delete(titleKey);
      formData.delete(descriptionKey);
      i++;
    }
  });

  // clear old valeus that are not going to be read by Spring controller (no need to send overhead aka additional files etc.)
  cleanFormData(formData, ["images-input", "prod-material", "materials"]);
  return await serverFetchAction<MultiLangFormState>({
    endpoint: productId ? `admin/products/${productId}` : "admin/products",
    method: productId ? "PUT" : "POST",
    body: formData,
    alternativeOk: () => revalidatePath("/", "layout"),
    isMultipart: true,
  });
};

export default createOrUpdateProduct;
