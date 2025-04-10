"use client";

import { Locale } from "@/types/common";
import { defaultLocale } from "@/constants/locales";

export const getLocale = function (): Locale {
  return (typeof document !== "undefined" ? document.documentElement.lang : defaultLocale) as Locale;
};

export const isClient = () => typeof document !== "undefined";
