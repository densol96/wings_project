import { Locale, RandomProductDto } from "@/types";
import React from "react";
import AnimatedProductImage from "./AnimatedProductImage";
import { Button, Heading } from "../ui";
import Link from "next/link";
import { cn, formatPrice, slugify } from "@/utils";

type Props = {
  product: RandomProductDto;
  lang: Locale;
};

const dict = {
  en: {
    btn: "View Product",
    available: "Available",
    notAvailable: "Not available",
  },
  lv: {
    btn: "Apskatīt Produktu",
    available: "Pieejams",
    notAvailable: "Nav pieejams",
  },
};

const RandomProduct = ({ product, lang }: Props) => {
  return (
    <div key={product.id} className="lg:py-20 px-10 py-6">
      <div
        key={product.id}
        className="shadow-custom-med lg:h-[350px] h-auto flex lg:flex-row flex-col items-center lg:gap-32 gap-2 rounded-b-none lg:rounded-3xl border-primary-bright bg-gradient-to-t from-light-nav to-white shadow-custom-def overflow-hidden lg:overflow-visible rounded-t-3xl"
      >
        <AnimatedProductImage
          images={product.imageDtos}
          lang={lang}
          className={cn(
            "h-[300px] w-full lg:h-full lg:w-[35%] lg:ml-28 transform lg:scale-125 scale-100 bg-gray-50 lg:shadow-custom-med shadow-none rounded-b-none  lg:rounded-3xl",
            product.amount === 0 && "overflow-visible"
          )}
          isNotAvailable={product.amount === 0}
          imagePosition="object-[50%_50%]"
        />
        <div className="flex-1 sm:p-6 sm:pb-20 py-10 lg:py-0 lg:pr-10 pr-0 flex flex-col sm:flex-row lg:flex-col gap-0 md:gap-10 lg:gap-0">
          <div className="">
            <Heading size="xl" className="text-gray-600">
              {product.translationDto.title}
            </Heading>
            <p className="text-2xl font-bold">{formatPrice(product.price)}</p>
            <p className="mt-2">{product.translationDto.description}</p>
            <p className={cn("font-bold mt-6", product.amount > 0 ? "text-green-700" : "text-red-700")}>
              {product.amount > 0 ? `${dict[lang].available}: ${product.amount}` : dict[lang].notAvailable}
            </p>
          </div>
          <div className="flex lg:justify-end justify-center items-end mt-10">
            <Link href={`/${lang}/shop/products/${product.id}-${slugify(product.translationDto?.title)}`}>
              <Button>{dict[lang].btn}</Button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RandomProduct;
