"use client";

import Link from "next/link";
import React, { useState } from "react";

import { NavBar } from "@/components/shared";
import { Logo } from "@/components/ui";
import { ToolsIcon, ExpandIcon, CloseIcon, CartIcon } from "@/components/ui/icons";
import { HeaderDictionary } from "./NavBar";
import LangSelectMenu from "./LangSelectMenu";

type Props = {
  className?: string;
  children?: React.ReactNode;
  headerDictionary: HeaderDictionary;
  lang: "lv" | "en";
};

const Header = ({ headerDictionary, lang }: Props) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const toggle = () => setIsExpanded(!isExpanded);

  return (
    <nav
      className={`${
        isExpanded ? "h-96" : "h-24"
      } p-1 sticky top-0 transition-all shadow-md lg:h-40 flex justify-between items-center overflow-y-hidden bg-light-nav lg:text-lg z-50`}
    >
      <div className={`${isExpanded ? "items-start" : "items-center"} justify-center flex h-full shrink p-4`}>
        <img
          draggable="false"
          className={`${isExpanded ? "invisible" : "opacity-100"} absolute -left-20 rotate-75 w-96 h-auto lg:visible lg:opacity-100`}
          src="/assets/prievites_nobackground.png"
          alt="Prievites-bilde"
        />
        <Logo />
      </div>

      <NavBar isExpanded={isExpanded} headerDictionary={headerDictionary} />

      <section className="flex flex-col items-end h-full">
        <button
          onClick={toggle}
          className="size-10 flex  justify-center align-center items-center lg:hidden"
          aria-label={!toggle ? "Show navigation" : "Hide navigation"}
        >
          {!isExpanded ? <ExpandIcon /> : <CloseIcon />}
        </button>

        <div className={`flex gap-x-4 h-full p-4 items-end ${isExpanded ? "flex-col-reverse" : "flex-row"} lg:flex-row gap-4 lg:gap-2`}>
          <LangSelectMenu lang={lang} />
          <div className="flex flex-row gap-4 lg:gap-2">
            <Link className="size-6 self-end" href="/admin">
              <ToolsIcon />
            </Link>
            <button className="size-6 self-end">
              <CartIcon />
            </button>
          </div>
        </div>
      </section>
    </nav>
  );
};

export default Header;
