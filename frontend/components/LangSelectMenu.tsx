"use client";

import React, { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { locales, defaultLocale } from "@/constants/locales";
import { defaultLangIsSelected } from "@/services/helpers";

type Locales = "lv" | "en";

type Props = {
  lang: Locales;
};

const LangSelectMenu = ({ lang }: Props) => {
  const router = useRouter();
  const pathname = usePathname();
  const [selectedLang, setSelectedLang] = useState(lang);

  useEffect(() => {
    const newLocale = "/" + selectedLang;
    const newPath = defaultLangIsSelected(pathname) ? newLocale + pathname : pathname.replace(/^\/[a-z]{2}/, newLocale);
    router.push(newPath, { scroll: false });
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

export default LangSelectMenu;
