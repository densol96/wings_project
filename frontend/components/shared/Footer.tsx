import React from "react";
import Link from "next/link";

import { Button, Heading, Input, Logo } from "@/components/ui";
import LogoWithIcons from "./LogoWithIcons";
import { FooterProps } from "@/types/sections/footer";
import SubscribeForm from "./SubscribeForm";

const Footer = ({ footerDictionary, lang }: FooterProps) => {
  return (
    <footer className="w-full body-font mt-auto flex flex-col text-gray-700 bg-gray-200">
      <main className="w-[70%] mx-auto pt-24 pb-14 grid grid-cols-[repeat(auto-fit,minmax(150px,1fr))] gap-8 text-center">
        <LogoWithIcons title={footerDictionary.title} />
        {footerDictionary.categories.map((category, index) => (
          <div key={index} className="w-full">
            <h2 className="mb-3 text-sm font-bold tracking-widest text-gray-800 uppercase">{category.title}</h2>
            <nav className="mb-10 list-none">
              {category.links.map((cl, i) => (
                <li key={cl.href + "_column_" + i} className="mt-3">
                  <Link href={cl.href} className="text-gray-500 hover:text-gray-900 hover:cursor-pointer">
                    {cl.title}
                  </Link>
                </li>
              ))}
            </nav>
          </div>
        ))}
        <SubscribeForm lang={lang} subscribeSection={footerDictionary.subscribeSection} />
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
