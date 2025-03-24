"use client";

import React from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";

export type HeaderDictionary = {
  [key: string]: {
    title: string;
    href: string;
  };
};

type Props = {
  isExpanded: boolean;
  headerDictionary: HeaderDictionary;
};

const classOptions = {
  active:
    "p-3 font-bold opacity-100 text-shadow-sm shadow-neutral-500 tracking-wider relative opacity-100 before:content-[''] before:w-0  before:duration-150 before:bg-amber-900 before:rounded-md before:absolute before:bottom-0 before:left-0 before:w-full before:h-0.5",
  default:
    "p-3 tracking-wider text-shadow-sm transition-opacity  active:scale-50  duration-200 hover:shadow-neutral-500 opacity-65 relative hover:opacity-90 before:content-[''] before:w-0  before:duration-150 before:bg-amber-900 before:rounded-md before:absolute before:bottom-0 before:left-0 hover:before:w-full before:h-0.5",
};

const NavBar: React.FC<Props> = ({ isExpanded, headerDictionary }) => {
  const pathname = usePathname();
  const { active, default: def } = classOptions;

  return (
    <div
      className={`${
        isExpanded ? "opacity-100 grow" : `hidden`
      } text-gray-900 shrink-0 z-10 p-10 bg-cover flex justify-center items-center  bg-[url('/assets/knitting_img.jpg')] h-full bg-no-repeat bg-center rounded-xl overflow-hidden lg:block lg:grow-0`}
    >
      <ul
        className={`${
          isExpanded ? "opacity-100" : `invisible divide-x`
        } divide-amber-900 bg-light-nav bg-opacity-80 rounded-xl transition-opacity p-4 backdrop-opacity-10 opacity-0 duration-500 flex flex-col items-center gap-y-8 lg:opacity-100 lg:visible lg:flex lg:static lg:flex-row lg:-translate-x-0 lg:translate-y-0`}
      >
        {Object.values(headerDictionary).map((item) => {
          const isAnIndexPage = ["/", "/en"].includes(item.href);
          return (
            <li key={item.title}>
              <Link className={(isAnIndexPage ? item.href == pathname : pathname.startsWith(item.href)) ? active : def} href={item.href}>
                {item.title}
              </Link>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default NavBar;
