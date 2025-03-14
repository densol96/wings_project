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
  return (
    <>
      {header}
      <div className="grid grid-cols-[15rem_1fr] gap-8 ">
        <ul className="border-2 border-red-700">
          {categoryList.map((category) => (
            <Link key={category.title} href={`${category.id}-${slugify(category.title)}`}>
              <li>{category.title}</li>
            </Link>
          ))}
        </ul>
        <div className="border-2 border-red-700">{children}</div>
      </div>
    </>
  );
};

export default Layout;
