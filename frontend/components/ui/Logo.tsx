"use client";

import Link from "next/link";
import React from "react";
import Image from "next/image";

import logo from "@/public/biedribas_logo.png";
import { cn, getLocale } from "@/utils";
import { useLangContext } from "@/context/LangContext";

type Props = {
  hasBackground?: boolean;
};

const dict = {
  en: "Association logo",
  lv: "Biedrības logo",
};

const Logo = ({ hasBackground = true }) => {
  const { lang } = useLangContext();
  console.log("LOGO!");
  return (
    <Link
      className={cn("z-10 lg:h-24 shrink bg-opacity-80 backdrop-blur-nano rounded-full max-w-[133px] sm:max-w-[200px] w-full", hasBackground && "bg-light-nav")}
      href="/"
    >
      <Image src={logo} alt={dict[lang]} />
    </Link>
  );
};

export default Logo;
