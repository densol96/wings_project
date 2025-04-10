"use client";

import Link from "next/link";
import React from "react";

import { NavBar } from "@/components/shared";
import { Logo } from "@/components/ui";
import { ToolsIcon, ExpandIcon } from "@/components/ui/icons";
import LangSelectMenu from "./LangSelectMenu";
import { NavigationDictionary } from "@/types";
import { useSidebarContext } from "@/context/SidebarContext";
import CartIconLink from "./CartIconLink";

type Props = {
  navMenu: NavigationDictionary;
};

const Header = ({ navMenu }: Props) => {
  const { isOpen, close, open, toggle } = useSidebarContext();

  return (
    <nav className={`h-24 lg:h-40 sticky top-0 transition-all shadow-md flex justify-between items-center bg-light-nav text-lg z-10`}>
      <div className={`items-center justify-center flex h-full relative shrink p-4 overflow-hidden`}>
        <img draggable="false" className={`opacity-100 absolute left-0 rotate-75 w-96 h-auto`} src="/assets/prievites_nobackground.png" alt="Prievites-bilde" />
        <Logo />
      </div>

      <NavBar navMenu={navMenu} />

      <div className="flex flex-row gap-4 lg:gap-2 mt-auto mr-5 mb-5">
        <LangSelectMenu />
        {/* <Link className="size-10 self-end" href="/admin">
            <ToolsIcon />
          </Link> */}
        <button className="size-10 self-end">
          <CartIconLink />
        </button>
        <button
          onClick={toggle}
          className="size-10 flex  justify-center align-center items-center lg:hidden"
          aria-label={!toggle ? "Show navigation" : "Hide navigation"}
        >
          {!isOpen && <ExpandIcon />}
        </button>
      </div>
    </nav>
  );
};

export default Header;
