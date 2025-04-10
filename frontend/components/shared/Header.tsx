"use client";

import Link from "next/link";
import React, { useState } from "react";

import { NavBar } from "@/components/shared";
import { Logo } from "@/components/ui";
import { ToolsIcon, ExpandIcon, CloseIcon, CartIcon } from "@/components/ui/icons";
import LangSelectMenu from "./LangSelectMenu";
import { Locale, NavigationDictionary } from "@/types";
import { useSidebarContext } from "@/context/SidebarContext";

type Props = {
  lang: Locale;
  navMenu: NavigationDictionary;
};

const Header = ({ lang, navMenu }: Props) => {
  const { isOpen, close, open, toggle } = useSidebarContext();

  return (
    <nav className={`h-24 lg:h-40 sticky top-0 transition-all shadow-md flex justify-between items-center bg-light-nav text-lg z-10`}>
      <div className={`items-center justify-center flex h-full relative shrink p-4 overflow-hidden`}>
        <img draggable="false" className={`opacity-100 absolute left-0 rotate-75 w-96 h-auto`} src="/assets/prievites_nobackground.png" alt="Prievites-bilde" />
        <Logo />
      </div>

      <NavBar navMenu={navMenu} />

      <section className="flex flex-col justify-between items-end h-full py-1 mr-2">
        <button
          onClick={toggle}
          className="size-10 flex  justify-center align-center items-center lg:hidden"
          aria-label={!toggle ? "Show navigation" : "Hide navigation"}
        >
          {!isOpen && <ExpandIcon />}
        </button>

        <div className="flex flex-row gap-4 lg:gap-2 mt-auto">
          <LangSelectMenu />
          <Link className="size-6 self-end" href="/admin">
            <ToolsIcon />
          </Link>
          <button className="size-6 self-end">
            <CartIcon />
          </button>
        </div>
      </section>
    </nav>
  );
};

export default Header;
