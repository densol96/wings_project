"use server";
import { Locale, MultiLangFormState, TranslationMethod } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { defaultLocale, localeCodes } from "@/constants/locales";
import { revalidatePath } from "next/cache";

export const createProduct = async (prevState: MultiLangFormState, formData: FormData): Promise<MultiLangFormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  // const numericPermissionIds = formData.getAll("permissions").map((id) => Number(id));

  const translations: { locale: Locale; title: string; description?: string }[] = [];

  localeCodes.forEach((localeCode) => {
    if (localeCode === defaultLocale || formDataSerialised.translateMethod === TranslationMethod.MANUAL) {
      translations.push({
        locale: localeCode,
        title: formDataSerialised[`title-${localeCode}`] as string,
        description: (formDataSerialised[`description-${localeCode}`] as string) || undefined,
      });
    }
  });

  const body = {
    translationMethod: formDataSerialised.translationMethod,
    price: formDataSerialised.price,
    amount: formDataSerialised.amount,
    translations,
  };

  console.log(body);

  const result = await serverFetchAction<MultiLangFormState>({
    endpoint: "admin/products",
    method: "POST",
    body,
    alternativeOk: () => revalidatePath("/", "layout"),
  });

  console.log(result);

  return result;
};

export default createProduct;
