import { SidebarProvider } from "@/context/SidebarContext";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug } from "@/types";
import { CategoriesDict, CategoryLi, ProductSearchParams } from "@/types/sections/shop";
import { fetcher } from "@/utils";
import React from "react";
import CategoriesSidebar from "./CategoriesSidebar";

type Props = PagePropsWithSlug & {
  searchParams: ProductSearchParams;
  children: React.ReactNode;
  header: React.ReactNode;
};

export const revalidate = 0;

const Layout = ({ params: { lang }, header, children }: Props) => {
  return (
    <SidebarProvider>
      {header}
      <div className="grid grid-cols-1 md:grid-cols-[15rem_1fr] gap-20 mt-10">
        <CategoriesSidebar lang={lang} />
        <div>{children}</div>
      </div>
    </SidebarProvider>
  );
};

export default Layout;
