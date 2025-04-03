import { Button, Gallery, Heading, MyImage } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug, ProductDict, ProductDto, ShopDict } from "@/types";
import { capitalize, extractIdFromSlug, fetcher, formatPrice } from "@/utils";
import syncSlug from "@/utils/syncSlug";
import React from "react";
import ProductDisplay from "./ProductDisplay";
import Link from "next/link";
import AdditionalInfo from "./AdditionalInfo";
import ProductInfo from "./ProductInfo";

type Props = {};

const Page = async ({ params: { lang, slug } }: PagePropsWithSlug) => {
  const productId = extractIdFromSlug(slug);
  const [dictResult, product] = await Promise.all([
    getDictionary(lang),
    fetcher<ProductDto>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products/${productId}?lang=${lang}`),
  ]);
  const dict: ProductDict = dictResult.product;
  syncSlug(productId, product.translationDto.title, slug);
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-10">
      <ProductDisplay lang={lang} images={product.imageDtos} />
      <ProductInfo lang={lang} product={product} dict={dict} />
    </div>
  );
};

export default Page;
