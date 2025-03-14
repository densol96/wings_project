"use client";

import React from "react";
import { getLocale } from "@/utils";

// const title = {
//   lv: "Produkts nav atrasts",
//   en: "Product Not Found",
// };

// const description = {
//   lv: "Mēs nevarējām atrast šo produktu. Lūdzu, pārbaudiet, vai ievadītā adrese ir pareiza.",
//   en: "We couldn’t find that product. Please check that the address you entered is correct",
// };

const title = {
  lv: "Kategorija nav atrasta",
  en: "Category Not Found",
};

const description = {
  lv: "Mēs nevarējām atrast šo kategoriju. Lūdzu, pārbaudiet, vai ievadītā adrese ir pareiza.",
  en: "We couldn’t find that category. Please check that the address you entered is correct",
};

const NotFound = () => {
  const locale = getLocale();

  return <div>Not Found!</div>;
};

export default NotFound;
