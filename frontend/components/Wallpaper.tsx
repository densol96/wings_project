import React from "react";

import bg from "@/public/assets/about_sparni.png";
import Image from "next/image";
import Link from "next/link";
import { FaHandPointRight } from "react-icons/fa";

import { Heading, StyledLink } from "@/components/shared";
import { HomeContent, Locale } from "@/@types/shared";

type Props = {
  homeDictionary: HomeContent;
  lang: Locale;
};

const Wallpaper = ({ homeDictionary, lang }: Props) => {
  return (
    <div className="relative overflow-hidden py-24 sm:py-32 my-4 px-6">
      <Image
        alt={homeDictionary.imageDescription}
        src={bg}
        className="absolute inset-0 -z-10 size-full object-cover object-right md:object-center"
      />
      <div className="mx-auto max-w-7xl p-6 lg:px-8 bg-light-nav backdrop-blur-sm rounded-md bg-opacity-75 shadow-custom-dark">
        <div className="text-primary-bright">
          <Heading size="2xl" className="text-primary-bright">
            {homeDictionary.title}
          </Heading>
          <div className="flex flex-row justify-between items-end">
            <p className="mt-8 text-lg font-medium text-pretty sm:text-xl/8">{homeDictionary.description}</p>
            <StyledLink href={`${lang}/about`}>{homeDictionary.button}</StyledLink>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Wallpaper;
