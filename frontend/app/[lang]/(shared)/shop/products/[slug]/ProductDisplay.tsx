import { Gallery, MyImage } from "@/components";
import { Locale } from "@/i18n-config";
import { ImageDto, Lang } from "@/types";
import React from "react";

type Props = {
  images: ImageDto[];
  lang: Locale;
};

const ProductDisplay = ({ images, lang }: Props) => {
  return (
    <div className="min-h-[400px] md:min-h-[600px] relative w-[80%] mx-auto overflow-hidde">
      {images.length <= 0 ? <MyImage className="object-center" lang={lang} /> : <Gallery images={images} />}
      {images.length > 1 && (
        <span className="pointer-events-none absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 p-6 bg-slate-100 rounded-lg opacity-60 font-bold text-xl group-hover:opacity-90 transition-opacity">
          {`1/${images.length}`}
        </span>
      )}
    </div>
  );
};

export default ProductDisplay;
