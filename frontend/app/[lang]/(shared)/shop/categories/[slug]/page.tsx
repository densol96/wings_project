import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps, PagePropsWithSlug } from "@/types";
import { ProductSearchParams, ProductSortType } from "@/types/sections/shop";
import ProductGrid from "./ProductGrid";

type Props = PagePropsWithSlug & {
  searchParams: ProductSearchParams;
};

export const generateMetadata = async ({ params }: PageProps) => {
  const dict = (await getDictionary(params.lang)).shop;
  return {
    title: dict.meta,
  };
};

const News = async function ({ params: { lang, slug }, searchParams }: Props) {
  const page = Number(searchParams.page) || 1;
  const sortBy = (searchParams.sortBy as ProductSortType) || "default";

  return <ProductGrid page={page} sortBy={sortBy} />;
};

export default News;
