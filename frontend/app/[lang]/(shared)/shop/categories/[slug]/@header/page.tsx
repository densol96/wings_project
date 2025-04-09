import Select from "@/components/ui/Select";
import { getDictionary } from "@/dictionaries/dictionaries";
import { ProductsPageProps, ShopDict } from "@/types/sections/shop";
import { extractIdFromSlug, fetcher, syncSlug, validateSearchParams } from "@/utils";
import Link from "next/link";
import React from "react";
import ToggleCategoriesSidebar from "../ToggleCategoriesSidebar";

type ActiveCategory = {
  title: string;
  description: string;
};

const Header = async ({ params: { lang, slug }, searchParams }: ProductsPageProps) => {
  const dict: ShopDict = (await getDictionary(lang)).shop;
  const categoryId = extractIdFromSlug(slug);
  const activeCategory: ActiveCategory =
    categoryId !== 0
      ? await fetcher(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories/${categoryId}?lang=${lang}`, [404, 400])
      : { title: dict.title, description: dict.description };
  const { page, sort, direction } = validateSearchParams(searchParams);
  syncSlug(categoryId, activeCategory.title, slug, `sort=${sort}&direction=${direction}&page=${page}`);

  return (
    <>
      <div className="flex justify-between uppercase mb-10 md:flex-row flex-col">
        <div className="text-gray-500 flex gap-2 items-center">
          <p className="hover:text-gray-700">
            <Link href={`/${lang}`}>{dict.toHome}</Link>
          </p>
          <span className="text-xl"> / </span>
          <p className="font-medium text-gray-700">{activeCategory.title}</p>
        </div>
        <div className="flex justify-between flex-row items-center sm:mt-0 mt-4">
          <ToggleCategoriesSidebar title={dict.categories.shortTitle} />
          <Select activeValue={`${sort}-${direction}`} selectDict={dict.select} />
        </div>
      </div>
      <p>{activeCategory.description}</p>
    </>
  );
};

export default Header;
