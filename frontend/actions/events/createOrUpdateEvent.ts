"use server";

import { Locale, MultiLangFormState, TranslationMethod } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { defaultLocale, localeCodes } from "@/constants/locales";
import { revalidatePath } from "next/cache";
import { cleanFormData } from "@/utils";

export const createOrUpdateEvent = async (formData: FormData, eventId?: number): Promise<MultiLangFormState> => {
  let i = 0;
  localeCodes.forEach((localeCode) => {
    if (localeCode === defaultLocale || formData.get("translationMethod") === TranslationMethod.MANUAL) {
      formData.append(`translations[${i}].locale`, localeCode);
      ["title", "description", "location"].forEach((field) => {
        const fieldKey = `${field}-${localeCode}`;
        const valuePerFieldKey = formData.get(`${field}-${localeCode}`);
        if (valuePerFieldKey) {
          formData.append(`translations[${i}].${field}`, valuePerFieldKey);
        }
        formData.delete(fieldKey);
      });
      i++;
    }
  });
  // clear old valeus that are not going to be read by Spring controller (no need to send overhead aka additional files etc.)
  cleanFormData(formData, ["images-input"]);
  const res = await serverFetchAction<MultiLangFormState>({
    endpoint: eventId ? `admin/events/${eventId}` : "admin/events",
    method: eventId ? "PUT" : "POST",
    body: formData,
    alternativeOk: () => revalidatePath("/", "layout"),
    isMultipart: true,
  });
  console.log(res);
  return res;
};

export default createOrUpdateEvent;
