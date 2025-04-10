"use client";

import React from "react";
import { NavigationDictionary } from "@/types";
import Link from "next/link";
import Sidebar from "./Sidebar";
import SearchForm from "./SearchForm";
import { cn, isAnIndexPage } from "@/utils";
import { usePathname } from "next/navigation";
import { useSidebarContext } from "@/context/SidebarContext";

type Props = {
  navMenu: NavigationDictionary;
};

const NavSidebar = ({ navMenu }: Props) => {
  const pathname = usePathname();
  const { close } = useSidebarContext();

  return (
    <Sidebar className="lg:hidden overflow-auto" breakpoint="lg">
      <SearchForm className="p-0" />
      <ul className="flex flex-col gap-2 mt-6 mb-10">
        {Object.values(navMenu).map((item) => {
          const isActiveTab = isAnIndexPage(item.href) ? item.href == pathname : pathname.startsWith(item.href);
          return (
            <li onClick={() => !isActiveTab && setTimeout(close, 300)} key={item.title + "_" + "nav_sidebar"}>
              <Link
                className={cn(
                  "w-full h-full block uppercase tracking-wider font-bold",
                  isActiveTab ? "text-gray-800 text-lg hover:cursor-default" : "text-gray-500 hover:text-gray-700"
                )}
                href={item.href}
              >
                {item.title}
              </Link>
            </li>
          );
        })}
      </ul>
    </Sidebar>
  );
};

export default NavSidebar;
