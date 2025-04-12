import { Heading, Sidebar } from "@/components";
import { CategoriesDict, CategoryLi } from "@/types/sections/shop";
import React from "react";
import CategoryList from "./CategoryList";
import { fetcher } from "@/utils";
import { getDictionary } from "@/dictionaries/dictionaries";
import { Locale } from "@/types";

type Props = {
  lang: Locale;
};

const CategoriesSidebar = async ({ lang }: Props) => {
  const [categoryList, categoriesDict] = await Promise.all([
    fetcher<CategoryLi[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories?lang=${lang}`),
    getDictionary(lang),
  ]);

  return (
    <Sidebar
      className="place-self-start lg:top-52 md:top-40 md:sticky md:h-auto md:w-auto md:z-0 md:p-0 md:shadow-none md:block md:translate-x-0"
      breakpoint="md"
    >
      <Heading size="xs" as="h2" className="uppercase font-bold text-gray-700 tracking-wide md:mb-6 mb-10 text-left md:mt-0 mt-20">
        {categoriesDict.shop.categories.title}
      </Heading>
      <CategoryList list={categoryList} />
    </Sidebar>
  );
};

export default CategoriesSidebar;
