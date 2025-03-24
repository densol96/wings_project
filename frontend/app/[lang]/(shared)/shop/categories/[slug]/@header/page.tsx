import Select from "@/components/ui/Select";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug } from "@/types";
import { ShopDict } from "@/types/sections/shop";
import { extractIdFromSlug, fetcher, slugify } from "@/utils";
import unslugify from "@/utils/unslugify";
import Link from "next/link";
import { redirect } from "next/navigation";
import React from "react";
import ToggleCategoriesSidebar from "../ToggleCategoriesSidebar";

type Props = {
  className?: string;
  children?: React.ReactNode;
};

type ActiveCategory = {
  title: string;
  description: string;
};

const Header = async ({ params: { lang, slug } }: PagePropsWithSlug) => {
  const dict: ShopDict = (await getDictionary(lang)).shop;
  const categoryId = extractIdFromSlug(slug);
  const activeCategory: ActiveCategory =
    categoryId !== 0
      ? await fetcher(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories/${categoryId}?lang=${lang}`, [404, 400])
      : { title: dict.title, description: dict.description };

  // If locale was changes, f.e. from /en/*/1-hats to /lv/*/hats-1 we get a localised activeCategory(cepures) and we can change the displayed url to /lv/*/1-cepures
  const newLocalisedSlug = `${categoryId}-${slugify(activeCategory.title)}`;
  if (newLocalisedSlug !== slug) redirect(newLocalisedSlug);

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
          <Select selectDict={dict.select} />
        </div>
      </div>
      <p>{activeCategory.description}</p>
    </>
  );
};

export default Header;
