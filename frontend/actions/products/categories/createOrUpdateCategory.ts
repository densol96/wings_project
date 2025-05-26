"use server";
import { serverFetchAction } from "@/actions/helpers/serverFetchAction";
import { defaultLocale, localeCodes } from "@/constants/locales";
import { Locale, MultiLangFormState, TranslationMethod } from "@/types";
import { revalidatePath } from "next/cache";

type CategoryTranslation = {
  locale: Locale;
  title: string | null;
  description: string | null;
};

export const createOrUpdateCategory = async (prevState: MultiLangFormState, formData: FormData): Promise<MultiLangFormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());

  const translationMethod = formDataSerialised.translationMethod as string;
  const translations: CategoryTranslation[] = [];

  localeCodes.forEach((localeCode) => {
    if (localeCode === defaultLocale || formDataSerialised.translationMethod === TranslationMethod.MANUAL) {
      const title = formDataSerialised[`title-${localeCode}`];
      const description = formDataSerialised[`description-${localeCode}`];
      translations.push({
        locale: localeCode,
        title: typeof title === "string" ? title : null,
        description: typeof description === "string" && description ? description : null,
      });
    }
  });

  const id = formData.get("id");
  const body = {
    translationMethod,
    translations,
  };

  const res = await serverFetchAction<MultiLangFormState>({
    endpoint: id ? `admin/products/categories/${id}` : "admin/products/categories",
    method: id ? "PUT" : "POST",
    body,
    alternativeOk: () => {
      revalidatePath("/", "layout");
    },
  });

  console.log(res);

  return res;
};

export default createOrUpdateCategory;
