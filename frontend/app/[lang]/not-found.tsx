"use client";

import { useLangContext } from "@/context";

export default function GlobalNotFound() {
  const { lang } = useLangContext();

  const translations = {
    lv: {
      title: "Lapa nav atrasta",
      description: "Iespējams, tā ir izdzēsta vai nepastāv.",
    },
    en: {
      title: "Page not found",
      description: "It may have been deleted or does not exist.",
    },
  };

  const t = translations[lang] ?? translations.lv;

  return (
    <div className="text-center my-36">
      <h1 className="text-2xl font-bold">{t.title}</h1>
      <p className="mt-2 text-gray-600">{t.description}</p>
    </div>
  );
}
