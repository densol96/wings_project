import { Locale } from "@/i18n-config";
import { ProductDict, ShortProductCategoryDto } from "@/types";
import Link from "next/link";
import React from "react";

type Props = { lang: Locale; dict: ProductDict; category: ShortProductCategoryDto };

const ProductBreadcrumbs = ({ lang, dict, category }: Props) => (
  <div className="text-gray-500 flex gap-2 items-center uppercase text-sm tracking-wide">
    <p className="hover:text-gray-700">
      <Link href={`/${lang}`}>{dict.toHome}</Link>
    </p>
    <span className="text-xl"> / </span>
    <p className="hover:text-gray-700">
      <Link href={`/${lang}/shop/categories/${category.id}`}>{category.title}</Link>
    </p>
  </div>
);

export default ProductBreadcrumbs;
