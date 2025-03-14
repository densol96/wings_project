"use client";

import React, { ChangeEvent, useEffect, useState } from "react";
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

  const assembleNewUrl = (newLocale: string) => {
    return defaultLangIsSelected(pathname) ? newLocale + pathname : pathname.replace(/^\/[a-z]{2}/, newLocale);
  };

  useEffect(() => {
    const newPath = assembleNewUrl("/" + selectedLang);
    router.push(newPath, { scroll: false });
  }, [selectedLang]);

  const handleSelectChange = (e: ChangeEvent<HTMLSelectElement>) => {
    /*
    Had to do this because on a news-item's (event's) page (f.e. /news/1) LightGallery component is used, but it is not the best optimsied for Next.js. 
    Navigating using the router from next/naviagtion does not work properly. 
    Therefore, am doing a hard page refresh on that page only.
    */
    if (pathname.includes("/news/")) {
      window.location.href = assembleNewUrl("/" + e.target.value);
    } else {
      setSelectedLang(e.target.value as Locales);
    }
  };

  return (
    <select className="bg-transparent text-base cursor-pointer" value={selectedLang} onChange={handleSelectChange}>
      <option value="lv">Latviešu 🇱🇻</option>
      <option value="en">English 🇬🇧</option>
    </select>
  );
};

export default LangSelectMenu;
