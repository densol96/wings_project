import { Heading } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps, PagePropsWithSlug } from "@/types";
import { extractIdFromSlug, fetcher, slugify } from "@/utils";
import unslugify from "@/utils/unslugify";
import Link from "next/link";
import React from "react";

type Props = PagePropsWithSlug & {
  children: React.ReactNode;
  header: React.ReactNode;
};

type CategoryLi = {
  id: number | null;
  title: string;
  productsTotal: number;
};

export const revalidate = 0;

const Layout = async ({ params: { lang, slug }, header, children }: Props) => {
  const categoryList = await fetcher<CategoryLi[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories?lang=${lang}`);
  const categoryListTitle = (await getDictionary(lang)).shop.categoryListTitle;
  return (
    <>
      {header}
      <div className="grid grid-cols-[15rem_1fr] gap-20 mt-10">
        <aside className="">
          <Heading size="xs" as="h2" className="uppercase font-bold text-gray-700 tracking-wide mb-6">
            {categoryListTitle}
          </Heading>
          <ul className="flex flex-col gap-2 text-lg">
            {categoryList.map((category) => {
              return (
                <Link key={category.title} href={`${category.id}-${slugify(category.title)}`}>
                  <li className="flex justify-between items-center text-gray-500">
                    <p>{category.title}</p>
                    <p className="text-sm">({category.productsTotal})</p>
                  </li>
                </Link>
              );
            })}
          </ul>
        </aside>
        <div className="">{children}</div>
      </div>
    </>
  );
};

export default Layout;
