import React from "react";
import { ShopDict, ShortProductDto } from "@/types/sections/shop";
import { cn, formatPrice, slugify } from "@/utils";
import { MyImage } from "@/components";
import { Locale } from "@/types";
import Link from "next/link";
import { BsCartPlusFill } from "react-icons/bs";

type Props = {
  className?: string;
  product: ShortProductDto;
  lang: Locale;
  dict: ShopDict;
};

const ProductCard = ({ className, product, lang, dict }: Props) => {
  const imagesTotal = product.imageDtos?.length || 0;
  const href = `/${lang}/shop/products/${product.id}-${slugify(product.translationDto?.title)}`;

  return (
    <article className={cn("shadow-2xl pb-8", className)}>
      <Link href={href} className="min-h-[250px] relative block">
        {/* Should be 2 coming from the backend, but front-end logic will handle if less as well */}
        {imagesTotal === 0 && <MyImage />}
        {product.imageDtos.map(
          (img, i) =>
            i < 2 && (
              <MyImage
                key={img.alt + "_" + i}
                withEffect={false}
                image={img}
                lang={lang}
                className={cn(
                  "transition duration-500",
                  i === 0 && "opacity-100",
                  i === 0 && imagesTotal > 1 && "hover:opacity-0",
                  i !== 0 && imagesTotal > 1 && "opacity-0 hover:opacity-100"
                )}
              />
            )
        )}
        {product.amount === 0 && (
          <div className="absolute top-1/2 w-full text-center py-6 bg-gray-200 opacity-85 uppercase font-medium -translate-y-1/2">Nav noliktavā</div>
        )}
      </Link>
      <div className="px-6">
        <Link className="text-gray-500 hover:underline mt-6 block" href={href}>
          {product.translationDto.title}
        </Link>
        <p className="text-sm font-bold">{formatPrice(product.price)}</p>
        <p className="flex justify-center mt-4">
          <button className="flex gap-2 items-center hover:underline">
            <BsCartPlusFill size={24} />
            <span>{dict.addToCartBtn}</span>
          </button>
        </p>
      </div>
    </article>
  );
};

export default ProductCard;
