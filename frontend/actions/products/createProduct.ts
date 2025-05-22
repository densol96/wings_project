"use server";
import { Locale, MultiLangFormState, TranslationMethod } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { defaultLocale, localeCodes } from "@/constants/locales";
import { revalidatePath } from "next/cache";

export const createProduct = async (formData: FormData): Promise<MultiLangFormState> => {
  // const formDataFields= Object.fromEntries(formData.entries());

  const translations: { locale: Locale; title: FormDataEntryValue; description: FormDataEntryValue | null }[] = [];

  // localeCodes.forEach((localeCode) => {
  //   if (localeCode === defaultLocale || formDataSerialised.translateMethod === TranslationMethod.MANUAL) {
  //     translations.push({
  //       locale: localeCode,
  //       title: formDataSerialised[`title-${localeCode}`] as string,
  //       description: (formDataSerialised[`description-${localeCode}`] as string) || undefined,
  //     });
  //   }
  // });

  localeCodes.forEach((localeCode) => {
    if (localeCode === defaultLocale || formData.get("translateMethod") === TranslationMethod.MANUAL) {
      translations.push({
        locale: localeCode,
        title: formData.get(`title-${localeCode}`) || "",
        description: formData.get(`description-${localeCode}`),
      });
    }
  });

  // const data = new FormData();
  // data.append("translationMethod", formDataSerialised.translationMethod);
  // data.append("price", formDataSerialised.price);
  // data.append("amount", formDataSerialised.amount);

  translations.forEach((tr, index) => {
    formData.append(`translations[${index}].locale`, tr.locale);
    formData.append(`translations[${index}].title`, tr.title);
    if (tr.description) {
      formData.append(`translations[${index}].description`, tr.description);
    }
  });

  const images = formData.getAll("images") as File[];
  images.forEach((image) => {
    formData.append("images", image);
  });

  return await serverFetchAction<MultiLangFormState>({
    endpoint: "admin/products",
    method: "POST",
    body: formData,
    alternativeOk: () => revalidatePath("/", "layout"),
    isMultipart: true,
  });
};

export default createProduct;
