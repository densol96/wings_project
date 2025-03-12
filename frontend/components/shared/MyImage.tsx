import { Image as ImageType } from "@/@types/shared";
import { Locale } from "@/i18n-config";
import Image from "next/image";
import React from "react";

type Props = {
  className?: string;
  image?: ImageType;
  lang?: Locale;
  withEffect?: boolean;
};

const MyImage = ({ className, image, lang = "lv", withEffect = true }: Props) => {
  return (
    <Image
      className={`object-cover object-top ${withEffect ? "transform transition duration-300 hover:scale-110" : ""} ${className || ""}`}
      fill
      src={image?.src || `https://www.allianceplast.com/wp-content/uploads/no-image.png`}
      alt={image?.alt || (lang == "lv" ? "Attēls nav pieejams" : "No image available")}
    />
  );
};

export default MyImage;
