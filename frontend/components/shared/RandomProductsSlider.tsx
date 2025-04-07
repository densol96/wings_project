"use client";

import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { Locale, ProductDto, SliderCardOption } from "@/types";
import { cn } from "@/utils";
import RandomProduct from "./RandomProduct";

import { FaCircleChevronLeft, FaCircleChevronRight } from "react-icons/fa6";

type Props = {
  randomProducts: ProductDto[];
  className?: string;
  lang: Locale;
  cardOption: SliderCardOption;
};

const CustomArrow = ({ direction, onClick }: { direction: "left" | "right"; onClick?: () => void }) => (
  <button
    onClick={onClick}
    className={cn(
      "absolute top-1/2 -translate-y-1/2 z-10 p-2 rounded-full bg-white shadow-md hover:bg-gray-100 transition text-primary-bright",
      direction === "left" ? "-left-2" : "-right-2"
    )}
  >
    {direction === "left" ? <FaCircleChevronLeft size={30} /> : <FaCircleChevronRight size={30} />}
  </button>
);

const settings = {
  infinite: true,
  slidesToShow: 1,
  slidesToScroll: 1,
  autoplay: true,
  autoplaySpeed: 4000,
  arrows: true,
  nextArrow: <CustomArrow direction="right" />,
  prevArrow: <CustomArrow direction="left" />,
  dots: true,
  appendDots: (dots) => (
    <div className="mt-4">
      <ul className="flex justify-center gap-2">{dots}</ul>
    </div>
  ),
  customPaging: (i) => <div className="w-3 h-3 rounded-full bg-gray-300 transition-all custom-dot" />,
};

const RandomProductsSlider: React.FC<Props> = ({ randomProducts, className, cardOption, lang }: Props) => {
  let mp = { x0: 0, y0: 0, x: 0, y: 0, md: false };

  const sliderCardOptions = {
    random: (product: ProductDto) => <RandomProduct lang={lang} product={product} />,
  };

  return (
    <div className={cn("my-40 overflow-visible relative px-4", className)}>
      <Slider {...settings}>{randomProducts.map(sliderCardOptions[cardOption])}</Slider>
    </div>
  );
};

export default RandomProductsSlider;
