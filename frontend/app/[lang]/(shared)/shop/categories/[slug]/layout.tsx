import { Heading } from "@/components";
import { CategoriesSidebarProvider } from "@/context/CategoriesSidebarContext";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug } from "@/types";
import { CategoriesDict, CategoryLi, ProductSearchParams } from "@/types/sections/shop";
import { fetcher } from "@/utils";
import Link from "next/link";
import React from "react";
import CategoriesSidebar from "./CategoriesSidebar";

type Props = PagePropsWithSlug & {
  searchParams: ProductSearchParams;
  children: React.ReactNode;
  header: React.ReactNode;
};

export const revalidate = 0;

const Layout = async ({ params: { lang, slug }, header, children, searchParams }: Props) => {
  const categoryList = await fetcher<CategoryLi[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories?lang=${lang}`);
  const categoriesDict: CategoriesDict = (await getDictionary(lang)).shop.categories;
  return (
    <CategoriesSidebarProvider>
      {header}
      <div className="grid grid-cols-1 md:grid-cols-[15rem_1fr] gap-20 mt-10">
        <CategoriesSidebar dict={categoriesDict} categoryList={categoryList} />
        <div>{children}</div>
      </div>
    </CategoriesSidebarProvider>
  );
};

export default Layout;
