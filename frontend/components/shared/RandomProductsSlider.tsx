"use client";

import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { Locale, ProductDto, RandomProductDto, SliderCardOption, SliderSettings } from "@/types";
import { cn } from "@/utils";
import RandomProduct from "./RandomProduct";

import { FaCircleChevronLeft, FaCircleChevronRight } from "react-icons/fa6";
import ProductCard from "@/app/[lang]/(shared)/shop/categories/[slug]/ProductCard";

type Props = {
  randomProducts: ProductDto[];
  className?: string;
  lang: Locale;
  cardOption: SliderCardOption;
  sliderSettings?: SliderSettings;
  dotsClassname?: string;
  arrowsClassname?: string;
};

const CustomArrow = ({ direction, onClick, arrowsClassname }: { direction: "left" | "right"; onClick?: () => void; arrowsClassname?: string }) => (
  <button
    onClick={onClick}
    className={cn(
      "absolute top-1/2 -translate-y-1/2 p-2 rounded-full bg-white shadow-md hover:bg-gray-100 transition text-primary-bright hidden xl:block",
      direction === "left" ? "-left-14" : "-right-14",
      arrowsClassname
    )}
  >
    {direction === "left" ? <FaCircleChevronLeft size={30} /> : <FaCircleChevronRight size={30} />}
  </button>
);

const viewBtn = {
  en: "View product",
  lv: "Skatīt produktu",
};

const RandomProductsSlider: React.FC<Props> = ({ randomProducts, className, cardOption, lang, sliderSettings, dotsClassname, arrowsClassname }: Props) => {
  let mp = { x0: 0, y0: 0, x: 0, y: 0, md: false };

  const sliderCardOptions = {
    random: (product: RandomProductDto, i: number) => <RandomProduct key={product.id + "_" + i} lang={lang} product={product} />,
    related: (product: RandomProductDto, i: number) => (
      <ProductCard key={product.id + "_" + i} className="shadow-none" viewBtn={viewBtn[lang]} lang={lang} product={product} />
    ),
  };

  const settings = {
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 4000,
    arrows: true,
    nextArrow: <CustomArrow arrowsClassname={arrowsClassname} direction="right" />,
    prevArrow: <CustomArrow arrowsClassname={arrowsClassname} direction="left" />,
    dots: true,
    appendDots: (dots) => (
      <div className={cn("mt-10 border-2 border-red-700")}>
        <ul className="flex justify-center gap-2">{dots}</ul>
      </div>
    ),
    customPaging: (i) => <div className={cn("w-3 h-3 rounded-full bg-gray-300 transition-all custom-dot", dotsClassname)} />,
  };

  const finalSettings = {
    ...settings,
    ...sliderSettings,
  };

  return (
    <div className={cn("my-40 overflow-visible relative", className)}>
      <Slider {...finalSettings}>{randomProducts.map(sliderCardOptions[cardOption])}</Slider>
    </div>
  );
};

export default RandomProductsSlider;
