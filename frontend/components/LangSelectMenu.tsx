"use client";

import React, { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { locales, defaultLocale } from "@/constants/locales";

type Locales = "lv" | "en";

type Props = {
  lang: Locales;
};

export const LangSelectMenu = ({ lang }: Props) => {
  const router = useRouter();
  const pathname = usePathname();
  const [selectedLang, setSelectedLang] = useState(lang);

  useEffect(() => {
    const defaultLangSelected = !locales.some((l) => pathname.startsWith("/" + l.locale));
    const newLocale = "/" + selectedLang;
    const newPath = defaultLangSelected ? newLocale + pathname : pathname.replace(/^\/[a-z]{2}/, newLocale);
    router.push(newPath);
  }, [selectedLang]);

  return (
    <select
      className="bg-transparent text-base cursor-pointer"
      value={selectedLang}
      onChange={(e) => setSelectedLang(e.target.value as Locales)}
    >
      <option value="lv">Latviešu 🇱🇻</option>
      <option value="en">English 🇬🇧</option>
    </select>
  );
};
