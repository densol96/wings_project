import React from "react";
import { ShopDict, ShortProductDto } from "@/types/sections/shop";
import { cn, formatPrice } from "@/utils";
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
  const imagesTotal = product.images?.length || 0;

  return (
    <article className={cn("shadow-2xl pb-8", className)}>
      <Link href={`/shop/products/${product.id}`} className="min-h-[250px] relative block">
        {/* Should be 2 coming from the backend, but front-end logic will handle if less as well */}
        {imagesTotal === 0 && <MyImage />}
        {product.images.map(
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
      </Link>
      <div className="px-6">
        <Link className="text-gray-500 hover:underline mt-6 block" href={`/shop/products/${product.id}`}>
          {product.translation.title}
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
