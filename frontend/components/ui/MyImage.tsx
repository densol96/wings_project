"use client";

import { ImageDto as ImageType } from "@/types/";
import { Locale } from "@/i18n-config";
import Image from "next/image";
import React from "react";
import { cn } from "@/utils";
import { defaultLocale } from "@/constants/locales";
import { useLangContext } from "@/context/LangContext";

type Props = {
  className?: string;
  image?: ImageType;
  withEffect?: boolean;
};

const altNoImage: Record<Locale, string> = {
  lv: "Attēls nav pieejams",
  en: "No image available",
};

const MyImage = ({ className, image, withEffect = true }: Props) => {
  const { lang } = useLangContext();
  return (
    <Image
      className={cn("object-cover", withEffect && "transform transition duration-300 hover:scale-110", !image?.src && "object-[0_50%]", className)}
      fill
      src={image?.src || `/no-image.png`}
      alt={image?.alt || altNoImage[lang || defaultLocale]}
    />
  );
};

export default MyImage;
