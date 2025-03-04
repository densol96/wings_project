import React from "react";
import { Logo } from "./shared";
import Link from "next/link";

type IconLink = {
  icon: React.JSX.Element;
  href: string;
};

type ColumnLink = {
  title: string;
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

type FooterLink = {
  title: string;
  href: string;
};

type FooterCategory = {
  title: string;
  links: FooterLink[];
};

export type Footer = {
  title: string;
  description: string;
  categories: FooterCategory[];
};

type Props = {
  footerDictionary: Footer;
};

const Footer = ({ footerDictionary }: Props) => {
  return (
    <footer className="w-full body-font mt-auto flex flex-col text-gray-700 bg-gray-200">
      <main className="px-40 pt-24 pb-14 grid grid-cols-[repeat(auto-fit,minmax(150px,1fr))] gap-8 text-center">
        <div className="w-full text-center">
          <Logo />
          <p className="mt-2 text-sm">{footerDictionary.title}</p>
          <div className="mt-4 flex justify-center space-x-3">
            {iconLinks.map((il, i) => (
              <a key={il.href + "_icon_" + i} href={il.href} className="hover:text-gray-700">
                {il.icon}
              </a>
            ))}
          </div>
        </div>
        {footerDictionary.categories.map((category, index) => (
          <div key={index} className="w-full">
            <h2 className="mb-3 text-sm font-bold tracking-widest text-gray-800 uppercase">{category.title}</h2>
            <nav className="mb-10 list-none">
              {category.links.map((cl, i) => (
                <li key={cl.href + "_column_" + i} className="mt-3">
                  <a className="text-gray-500 hover:text-gray-900 hover:cursor-pointer">{cl.title}</a>
                </li>
              ))}
            </nav>
          </div>
        ))}
      </main>
      <div className="bg-gray-300">
        <div className="px-5 py-4 text-center">
          <p className="text-sm">
            © {new Date().getFullYear()} {footerDictionary.description}
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
