import { Image as ImageType } from "@/types/";
import { Locale } from "@/i18n-config";
import Image from "next/image";
import React from "react";
import { cn } from "@/utils";

type Props = {
  className?: string;
  image?: ImageType;
  lang?: Locale;
  withEffect?: boolean;
};

const altNoImage: Record<Locale, string> = {
  lv: "Attēls nav pieejams",
  en: "No image available",
};

const MyImage = ({ className, image, lang = "lv", withEffect = true }: Props) => {
  return (
    <Image
      className={cn("object-cover", withEffect && "transform transition duration-300 hover:scale-110", !image?.src && "object-[0_50%]", className)}
      fill
      src={image?.src || `/no-image.png`}
      alt={image?.alt || altNoImage[lang]}
    />
  );
};

export default MyImage;
