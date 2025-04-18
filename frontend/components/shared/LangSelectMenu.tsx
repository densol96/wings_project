"use client";

import React, { ChangeEvent, useEffect, useState } from "react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { defaultLangIsSelected } from "@/services/helpers";
import { useLangContext } from "@/context/LangContext";

type Locales = "lv" | "en";

type Props = {
  temp: number;
};

const LangSelectMenu = ({}: Props) => {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const [selectedLang, setSelectedLang] = useState(useLangContext().lang);

  const assembleNewUrl = (newLocale: string) => {
    const oldUrl = `${pathname}?${searchParams.toString()}`;
    return defaultLangIsSelected(pathname) ? newLocale + oldUrl : oldUrl.replace(/^\/[a-z]{2}/, newLocale);
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
    if (pathname.includes("/news/") || pathname.includes("/jaunumi/") || pathname.includes("/shop/products/") || pathname.includes("/veikals/produkti/")) {
      window.location.href = assembleNewUrl("/" + e.target.value);
    } else {
      setSelectedLang(e.target.value as Locales);
    }
  };

  return (
    <select className="bg-transparent cursor-pointer text-lg" value={selectedLang} onChange={handleSelectChange}>
      <option value="lv">Latviešu 🇱🇻</option>
      <option value="en">English 🇬🇧</option>
    </select>
  );
};

export default LangSelectMenu;
