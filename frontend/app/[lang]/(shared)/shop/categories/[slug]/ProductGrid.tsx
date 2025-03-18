import { getDictionary } from "@/dictionaries/dictionaries";
import { Image, Lang, PageableResponse } from "@/types";
import { ProductSearchParams, ShopDict, ShortProductDto } from "@/types/sections/shop";
import { fetcher, parsePageableResponse } from "@/utils";
import React from "react";
import ProductCard from "./ProductCard";

type Props = Lang &
  ProductSearchParams & {
    categoryId: number;
  };

const ProductGrid = async ({ page, sort, direction, categoryId, lang }: Props) => {
  const queryParams = new URLSearchParams({
    categoryId: String(categoryId),
    sort,
    direction,
    page: String(page),
    lang,
  }).toString();

  const [dictResult, productsResult] = await Promise.all([
    getDictionary(lang),
    fetcher<PageableResponse>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products?${queryParams}`),
  ]);
  const { content: products, page: currentPage, size, totalPages, totalElements } = parsePageableResponse<ShortProductDto>(productsResult);
  const dict: ShopDict = dictResult.shop;

  return !products.length ? (
    <p className="text-center mt-20">{dict.noProductsMessage}</p>
  ) : (
    <section className="grid grid-cols-3 gap-x-6 gap-y-12">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} lang={lang} dict={dict} />
      ))}
    </section>
  );
};

export default ProductGrid;
