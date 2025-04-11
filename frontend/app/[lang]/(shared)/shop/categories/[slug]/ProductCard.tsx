"use client";

import React from "react";
import { ShopDict, ShortProductDto } from "@/types/sections/shop";
import { cn, createCartItem, formatPrice, slugify } from "@/utils";
import { AnimatedProductImage, MyImage } from "@/components";
import { Locale } from "@/types";
import Link from "next/link";
import { BsCartPlusFill } from "react-icons/bs";
import { TiTick } from "react-icons/ti";
import { GrView } from "react-icons/gr";
import { useCartContext } from "@/context";

type Props = {
  className?: string;
  product: ShortProductDto;
  lang: Locale;
  dict?: ShopDict;
  viewBtn?: string;
};

const viewBtn = {
  en: "View product",
  lv: "Skatīt produktu",
};

const ProductCard = ({ className, product, lang, dict, viewBtn }: Props) => {
  const { addProduct, productIsInCart, cartIsLoaded } = useCartContext();

  const href = `/${lang}/shop/products/${product.id}-${slugify(product.translationDto?.title)}`;

  const isInCart = productIsInCart(product.id);

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
        <div className="flex justify-center mt-4">
          {viewBtn && !dict && (
            <button className="flex gap-2 items-center hover:underline">
              <GrView size={24} />
              <span>{viewBtn}</span>
            </button>
          )}
          {!viewBtn &&
            dict &&
            cartIsLoaded &&
            (!isInCart ? (
              <button className="flex items-center gap-1" onClick={() => addProduct(createCartItem(product))}>
                <BsCartPlusFill size={24} />
                <span>{dict.addToCartBtn}</span>
              </button>
            ) : (
              <Link className="flex items-center gap-1" href={`/${lang}/checkout`}>
                <TiTick size={24} />
                <span>{dict.alreadyInCart}</span>
              </Link>
            ))}
        </div>
      </div>
    </article>
  );
};

export default ProductCard;
