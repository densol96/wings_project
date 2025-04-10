import "server-only";
import { Locale } from "@/types/";
import { defaultLocale } from "@/constants/locales";

type Dictionary = Record<string, any>;

const dictionaries: Record<Locale, () => Promise<Dictionary>> = {
  en: () => import("./en.json").then((module) => module.default),
  lv: () => import("./lv.json").then((module) => module.default),
};

const dictionaryCache = new Map<Locale, Promise<Dictionary>>();

export async function getDictionary(locale: Locale): Promise<Dictionary> {
  const lang = (locale as Locale) in dictionaries ? (locale as Locale) : defaultLocale;

  if (!dictionaryCache.has(lang)) {
    dictionaryCache.set(lang, dictionaries[lang]());
  }

  return dictionaryCache.get(lang)!;
}
