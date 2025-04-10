"use client";

import { Heading, Sidebar } from "@/components";
import { useSidebarContext } from "@/context/SidebarContext";
import { CategoriesDict, CategoryLi } from "@/types/sections/shop";
import React from "react";
import CategoryList from "./CategoryList";
import { usePathname } from "next/navigation";

type Props = {
  categoryList: CategoryLi[];
  dict: CategoriesDict;
};

const CategoriesSidebar = ({ dict, categoryList }: Props) => {
  return (
    <Sidebar
      className="place-self-start lg:top-52 md:top-40 md:sticky md:h-auto md:w-auto md:z-0 md:p-0 md:shadow-none md:block md:translate-x-0"
      breakpoint="md"
    >
      <Heading size="xs" as="h2" className="uppercase font-bold text-gray-700 tracking-wide md:mb-6 mb-10 text-left md:mt-0 mt-20">
        {dict.title}
      </Heading>
      <CategoryList list={categoryList} />
    </Sidebar>
  );
};

export default CategoriesSidebar;
