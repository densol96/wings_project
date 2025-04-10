"use client";

import { ImageDto, Locale } from "@/types";
import React from "react";
import { MyImage } from "../ui";
import { cn } from "@/utils";
import { useLangContext } from "@/context";

type Props = {
  images: ImageDto[];
  lang: Locale;
  className?: string;
  isNotAvailable: boolean;
  imagePosition?: string;
};

const dict = {
  lv: "Nav noliktavā",
  en: "Out of stock",
};

const AnimatedProductImage = ({ images, className, isNotAvailable, imagePosition }: Props) => {
  const imagesTotal = images?.length || 0;

  const { lang } = useLangContext();

  return (
    <div className={cn("relative overflow-hidden", className)}>
      {imagesTotal === 0 && <MyImage className={cn(imagePosition)} />}
      {images.map(
        (img, i) =>
          i < 2 && (
            <MyImage
              key={img.alt + "_" + i}
              withEffect={false}
              image={img}
              className={cn(
                "transition duration-500",
                imagePosition,
                i === 0 && "opacity-100",
                i === 0 && imagesTotal > 1 && "hover:opacity-0",
                i !== 0 && imagesTotal > 1 && "opacity-0 hover:opacity-100"
              )}
            />
          )
      )}
      {isNotAvailable && (
        <div className="absolute top-1/2 w-full text-center py-6 bg-gray-200 opacity-85 uppercase font-medium -translate-y-1/2">{dict[lang]}</div>
      )}
    </div>
  );
};

export default AnimatedProductImage;
