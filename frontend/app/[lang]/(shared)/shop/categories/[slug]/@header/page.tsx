import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug } from "@/types";
import { extractIdFromSlug, fetcher } from "@/utils";
import unslugify from "@/utils/unslugify";
import Link from "next/link";
import React from "react";

type Props = {
  className?: string;
  children?: React.ReactNode;
};

type ActiveCategory = {
  title: string;
  description: string;
};

type ShopDict = {
  title: string;
  description: string;
  toHome: string;
};

const Header = async ({ params: { lang, slug } }: PagePropsWithSlug) => {
  const dict: ShopDict = (await getDictionary(lang)).shop;
  const categoryId = extractIdFromSlug(slug);
  console.log(categoryId);
  const activeCategory: ActiveCategory =
    categoryId !== 0
      ? await fetcher(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories/${categoryId}?lang=${lang}`, [404, 400])
      : { title: dict.title, description: dict.description };

  return (
    <>
      <div className="flex justify-between uppercase mb-10">
        <div className="text-gray-500 flex gap-2 items-center">
          <p className="hover:text-gray-700">
            <Link href={`/${lang}`}>{dict.toHome}</Link>
          </p>
          <span className="text-xl"> / </span>
          <p>{activeCategory.title}</p>
        </div>
      </div>
      <p>{activeCategory.description}</p>
    </>
  );
};

export default Header;
