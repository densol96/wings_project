"use client";

import { CloseBtn, Heading, LogoWithIcons } from "@/components";
import { useSidebarContext } from "@/context/SidebarContext";
import { CategoriesDict, CategoryLi } from "@/types/sections/shop";
import { cn } from "@/utils";
import React, { useRef } from "react";
import CategoryList from "./CategoryList";

type Props = {
  className?: string;
  categoryList: CategoryLi[];
  dict: CategoriesDict;
};

const CategoriesSidebar = ({ className, dict, categoryList }: Props) => {
  const { isOpen, close } = useSidebarContext();
  const sidebar = useRef<HTMLElement | null>(null);

  const interactiveStyles =
    "fixed top-0 left-0 h-full w-[300px] z-50 bg-white opacity-100 p-8 shadow-2xl transition duration-450 transform -translate-x-full md:-translate-x-0";

  const closeSidebar = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (sidebar?.current && !sidebar.current.contains(e.target as Node)) close();
  };

  return (
    <div onClick={closeSidebar}>
      <div className={cn(isOpen && "bg-gray-700 opacity-75 z-40 fixed top-0 left-0 h-full w-full md:hidden")} />
      <CloseBtn className={cn("fixed top-10 right-10 z-50", !isOpen && "hidden")} />
      <aside
        ref={sidebar}
        className={cn(
          "place-self-start lg:top-52 md:top-40 md:sticky md:h-auto md:w-auto md:z-0 md:p-0 md:shadow-none md:block flex flex-col",
          interactiveStyles,
          isOpen && "translate-x-0",
          className
        )}
      >
        <Heading size="xs" as="h2" className="uppercase font-bold text-gray-700 tracking-wide md:mb-6 mb-10 text-left md:mt-0 mt-20">
          {dict.title}
        </Heading>
        <CategoryList list={categoryList} />
        <LogoWithIcons title={dict.footerTitle} className="mt-auto mb-40 md:hidden block" />
      </aside>
    </div>
  );
};

export default CategoriesSidebar;
