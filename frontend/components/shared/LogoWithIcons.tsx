"use client";

import { cn } from "@/utils";
import React from "react";
import { Logo } from "../ui";
import { useLangContext } from "@/context/LangContext";

type Props = {
  className?: string;
};

type IconLink = {
  icon: React.JSX.Element;
  href: string;
};

const iconLinks: IconLink[] = [
  {
    icon: (
      <svg fill="currentColor" className="w-5 h-5" viewBox="0 0 24 24">
        <path d="M18 2h-3a5 5 0 00-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 011-1h3z"></path>
      </svg>
    ),
    href: "/",
  },
  {
    icon: (
      <svg fill="currentColor" className="w-5 h-5" viewBox="0 0 24 24">
        <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2c9 5 20 0 20-11.5a4.5 4.5 0 00-.08-.83A7.72 7.72 0 0023 3z"></path>
      </svg>
    ),
    href: "/",
  },
  {
    icon: (
      <svg fill="none" stroke="currentColor" className="w-5 h-5" viewBox="0 0 24 24">
        <rect width="20" height="20" x="2" y="2" rx="5" ry="5"></rect>
        <path d="M16 11.37A4 4 0 1112.63 8 4 4 0 0116 11.37zm1.5-4.87h.01"></path>
      </svg>
    ),
    href: "/",
  },
];

const dict = {
  lv: "Rokdarbu izstrādājumi",
  en: "Handmade Products",
};

const LogoWithIcons = ({ className }: Props) => {
  const { lang } = useLangContext();

  return (
    <div className={cn("w-full text-center", className)}>
      <Logo />
      <p className="mt-2 text-sm">{dict[lang]}</p>
      <div className="mt-4 flex justify-center space-x-3">
        {iconLinks.map((il, i) => (
          <a key={il.href + "_icon_" + i} href={il.href} className="hover:text-gray-700">
            {il.icon}
          </a>
        ))}
      </div>
    </div>
  );
};

export default LogoWithIcons;
