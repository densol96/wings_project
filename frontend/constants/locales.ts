import { Locale } from "@/types";

const locales = [{ locale: "en" }, { locale: "lv" }] as { locale: Locale }[];

const defaultLocale = "lv";

const localeCodes = locales.map((l) => l.locale);

export { defaultLocale, locales, localeCodes };
