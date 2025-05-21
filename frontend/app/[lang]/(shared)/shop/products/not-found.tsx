"use client";

import { useLangContext } from "@/context";

export default function ProductNotFound() {
  const { lang } = useLangContext();

  const translations = {
    lv: {
      title: "Produkts nav atrasts",
      description: "Iespējams, tas ir izdzēsts vai nepastāv.",
    },
    en: {
      title: "Product not found",
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
