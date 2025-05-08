import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps, PagePropsWithSlug } from "@/types";
import { ProductSearchParams } from "@/types/sections/shop";
import ProductGrid from "./ProductGrid";
import { extractIdFromSlug, validateProductSearchParams } from "@/utils";
import { Suspense } from "react";
import { Spinner } from "@/components";

type Props = PagePropsWithSlug & {
  searchParams: ProductSearchParams;
};

export const generateMetadata = async ({ params }: PageProps) => {
  const dict = (await getDictionary(params.lang)).shop;
  return {
    title: dict.meta,
  };
};

const Products = async function ({ params: { lang, slug }, searchParams }: Props) {
  const { page, sort, direction } = validateProductSearchParams(searchParams);
  let categoryId = extractIdFromSlug(slug);
  categoryId = categoryId >= 0 ? categoryId : 0; // 0 for all products

  return (
    <Suspense fallback={<Spinner className="mt-20" />}>
      <ProductGrid page={page} sort={sort} direction={direction} categoryId={categoryId} lang={lang} />
    </Suspense>
  );
};

export default Products;
