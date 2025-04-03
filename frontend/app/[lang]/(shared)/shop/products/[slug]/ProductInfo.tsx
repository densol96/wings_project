import { Button, Heading } from "@/components";
import { Locale } from "@/i18n-config";
import { ProductDict, ProductDto } from "@/types";
import { cn, formatPrice } from "@/utils";
import Link from "next/link";
import React from "react";
import AdditionalInfo from "./AdditionalInfo";
import ProductBreadcrumbs from "./ProductBreadcrumbs";
import AddToCart from "./AddToCart";
import SocialLinks from "./SocialLinks";

type Props = {
  product: ProductDto;
  lang: Locale;
  dict: ProductDict;
};

const ProductInfo = ({ product, lang, dict }: Props) => {
  const isAvaiable = product.amount > 0;
  return (
    <div>
      <ProductBreadcrumbs lang={lang} dict={dict} category={product.categoryDto} />
      <Heading size="xl" className="text-gray-600">
        {product.translationDto.title}
      </Heading>
      <p className="text-2xl font-bold">{formatPrice(product.price)}</p>
      <p className="mt-2">{product.translationDto.description}</p>
      <AdditionalInfo product={product} dict={dict} />
      <p className={cn("font-bold mt-6", isAvaiable ? "text-green-700" : "text-red-700")}>
        {isAvaiable ? dict.isAvailable.replace("%%AMOUNT%%", product.amount + "") : dict.isNotAvailable}
      </p>
      <AddToCart amountTotal={product.amount} btnTitle={dict.addToCart} invalidAmountTitle={dict.invalidAmount} />
      <SocialLinks />
    </div>
  );
};

export default ProductInfo;
