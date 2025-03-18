import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps, PagePropsWithSlug, SortDirection } from "@/types";
import { ProductSearchParams, ProductSort } from "@/types/sections/shop";
import ProductGrid from "./ProductGrid";
import validateValues from "@/utils/validateValues";
import { extractIdFromSlug } from "@/utils";
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
  const pageNum = Number(searchParams.page);
  const page = pageNum && pageNum > 0 ? pageNum : 1;
  const sort = validateValues<ProductSort>(searchParams.sort, ["price", "createdAt"], "createdAt");
  const direction = validateValues<SortDirection>(searchParams.direction, ["asc", "desc"], "desc");
  let categoryId = extractIdFromSlug(slug);
  categoryId = categoryId >= 0 ? categoryId : 0;

  return (
    <Suspense fallback={<Spinner className="mt-20" />}>
      <ProductGrid page={page} sort={sort} direction={direction} categoryId={categoryId} lang={lang} />
    </Suspense>
  );
};

export default Products;
