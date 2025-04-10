"use client";

import React from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import SearchMenu from "./SearchMenu";
import { cn, isAnIndexPage } from "@/utils";
import { NavigationDictionary } from "@/types";

type Props = {
  navMenu: NavigationDictionary;
};

const classOptions = {
  active: `font-bold opacity-100 shadow-neutral-500 before:w-[80%]`,
  def: `transition-opacity active:scale-50 duration-200 hover:shadow-neutral-500 opacity-65 hover:opacity-90 hover:before:w-[80%]`,
};

const NavBar = ({ navMenu }: Props) => {
  const pathname = usePathname();

  return (
    <div className="xl:p-10 p-8 bg-cover flex justify-center items-center  bg-[url('/assets/knitting_img.jpg')] bg-no-repeat bg-center rounded-xl xl:text-lg text-sm hidden lg:block">
      <ul className="divide-x divide-amber-900 bg-light-nav bg-opacity-80 rounded-xl p-4 flex items-center divid text-gray-800">
        {Object.values(navMenu).map((item) => {
          return (
            <li key={item.title}>
              <Link
                className={cn(
                  "p-3 tracking-wider text-shadow-sm relative before:content-[''] before:w-0 before:duration-200 before:bg-amber-900 before:absolute before:bottom-0 before:left-[10%] before:h-0.5 ",
                  (isAnIndexPage(item.href) ? item.href == pathname : pathname.startsWith(item.href)) ? classOptions["active"] : classOptions["def"]
                )}
                href={item.href}
              >
                {item.title}
              </Link>
            </li>
          );
        })}
        <SearchMenu />
      </ul>
    </div>
  );
};

export default NavBar;
