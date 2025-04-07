import React from "react";
import RandomProductsSlider from "./RandomProductsSlider";
import { Locale, ProductDto, SliderCardOption } from "@/types";

type Props = {
  lang: Locale;
  categoryId?: number;
  amount?: number;
  className?: string;
  cardOption: SliderCardOption;
};

const RandomProducts = async ({ lang, className, cardOption, categoryId = 0, amount = 5 }: Props) => {
  const data = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products/random?categoryId=${categoryId}&amount=${amount}&lang=${lang}`, {
    next: { revalidate: 0 },
  });
  const randomProducts = (await data.json()) as ProductDto[];

  return randomProducts.length && <RandomProductsSlider cardOption={cardOption} lang={lang} className={className} randomProducts={randomProducts} />;
};

export default RandomProducts;
