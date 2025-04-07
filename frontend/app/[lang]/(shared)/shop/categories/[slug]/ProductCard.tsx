import React from "react";
import { ShopDict, ShortProductDto } from "@/types/sections/shop";
import { cn, formatPrice, slugify } from "@/utils";
import { AnimatedProductImage, MyImage } from "@/components";
import { Locale } from "@/types";
import Link from "next/link";
import { BsCartPlusFill } from "react-icons/bs";
import { GrView } from "react-icons/gr";

type Props = {
  className?: string;
  product: ShortProductDto;
  lang: Locale;
  dict?: ShopDict;
  viewBtn?: string;
};

const ProductCard = ({ className, product, lang, dict, viewBtn }: Props) => {
  const href = `/${lang}/shop/products/${product.id}-${slugify(product.translationDto?.title)}`;

  return (
    <article className={cn("shadow-2xl pb-8", className)}>
      <Link href={href}>
        <AnimatedProductImage images={product.imageDtos} lang={lang} className="min-h-[250px] relative" isNotAvailable={product.amount === 0} />
      </Link>
      <div className="px-6">
        <Link className="text-gray-500 hover:underline mt-6 block" href={href}>
          {product.translationDto.title}
        </Link>
        <p className="text-sm font-bold">{formatPrice(product.price)}</p>
        <p className="flex justify-center mt-4">
          <button className="flex gap-2 items-center hover:underline">
            {viewBtn && !dict && (
              <>
                <GrView size={24} />
                <span>{viewBtn}</span>
              </>
            )}
            {!viewBtn && dict && (
              <>
                <BsCartPlusFill size={24} />
                <span>{dict.addToCartBtn}</span>
              </>
            )}
          </button>
        </p>
      </div>
    </article>
  );
};

export default ProductCard;
