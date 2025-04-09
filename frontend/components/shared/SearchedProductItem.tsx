import { Locale, SearchedProductDto } from "@/types";
import Link from "next/link";
import React from "react";
import { MyImage } from "../ui";
import { cn, formatPrice, highlightWithDiacritics, isAvailableStyling } from "@/utils";

type Props = {
  product: SearchedProductDto;
  lang: Locale;
  query: string;
};

const SearchedProductItem = ({ product, lang, query }: Props) => {
  return (
    <Link href={`/${lang}/shop/products/${product.id}`}>
      <li className="flex items-center gap-2 hover:bg-gray-300 p-2 transition-colors duration-300 text-sm rounded-md">
        <div className="relative w-[40px] h-[40px] rounded-full overflow-hidden flex-shrink-0">
          <MyImage image={product.imageDto} />
        </div>
        <p className={cn(isAvailableStyling(product))}>{highlightWithDiacritics(product.title, query)}</p>
        <p className="ml-auto text-xs">{formatPrice(product.price)}</p>
      </li>
    </Link>
  );
};

export default SearchedProductItem;
