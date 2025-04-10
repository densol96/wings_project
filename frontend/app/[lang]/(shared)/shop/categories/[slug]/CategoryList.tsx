"use client";

import React from "react";
import { CategoryLi } from "@/types/sections/shop";
import { cn, extractActiveSegment, slugify } from "@/utils";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useSidebarContext } from "@/context/SidebarContext";

type Props = {
  className?: string;
  list: CategoryLi[];
};

const CategoryList = ({ className, list }: Props) => {
  const { close } = useSidebarContext();
  const segment = extractActiveSegment(usePathname());

  return (
    <ul className={cn("flex flex-col gap-2 text-lg", className)}>
      {list.map((category) => {
        const href = `${category.id}-${slugify(category.title)}`;
        return (
          <Link key={category.title} href={href}>
            <li
              onClick={() => segment === href && close()}
              className={cn(
                "flex justify-between items-center",
                segment === href ? "text-gray-800 font-medium cursor-default" : "text-gray-500 hover:text-gray-700"
              )}
            >
              <p>{category.title}</p>
              <p className="text-sm">({category.productsTotal})</p>
            </li>
          </Link>
        );
      })}
    </ul>
  );
};

export default CategoryList;
