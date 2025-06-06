import React from "react";
import RandomProductsSlider from "./RandomProductsSlider";
import { Locale, ProductDto, SliderCardOption, SliderSettings } from "@/types";

type Props = {
  lang: Locale;
  categoryId?: number;
  amount?: number;
  className?: string;
  cardOption: SliderCardOption;
  sliderSettings?: SliderSettings;
  dotsClassname?: string;
  idToExclude?: number;
};

const RandomProducts = async ({ lang, className, cardOption, categoryId = 0, amount = 5, sliderSettings, dotsClassname, idToExclude }: Props) => {
  const repsonse = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products/random?categoryId=${categoryId}&amount=${amount}&lang=${lang}`, {
    next: { revalidate: 10 },
  });
  const randomProducts = (await repsonse.json()) as ProductDto[];
  const filteredProducts = repsonse.ok ? randomProducts.filter((product) => product.id !== idToExclude) : [];
  return (
    randomProducts.length && (
      <RandomProductsSlider
        sliderSettings={sliderSettings}
        cardOption={cardOption}
        lang={lang}
        className={className}
        randomProducts={filteredProducts}
        dotsClassname={dotsClassname}
      />
    )
  );
};

export default RandomProducts;
