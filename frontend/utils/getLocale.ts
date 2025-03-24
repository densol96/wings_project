"use client";

import { Locale } from "@/types/common";

const getLocale = function (): Locale {
  return (typeof document !== "undefined" ? document.documentElement.lang : "lv") as Locale;
};

export default getLocale;
