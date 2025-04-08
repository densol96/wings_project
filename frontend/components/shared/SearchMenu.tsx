"use client";

import { cn } from "@/utils";
import React, { useState } from "react";
import { IoSearch } from "react-icons/io5";
import { Heading, RadioGroup } from "../ui";
import { Locale } from "@/types";
import { useDebounce } from "@reactuses/core";
import { useDebounceEffect } from "@/hooks";
import { search } from "@/actions/search";

type Props = {
  lang: Locale;
};

type CheckboxType = "news" | "products";

const dict = {
  en: {
    title: "Search through",
    news: {
      search: "Search news",
      label: "News",
    },
    products: {
      search: "Search products",
      label: "Produkts",
    },
  },
  lv: {
    title: "Pārlūkot saturu",
    news: {
      search: "Meklēt ziņas",
      label: "Ziņas",
    },
    products: {
      search: "Meklēt produktus",
      label: "Produkti",
    },
  },
};

const SearchMenu = ({ lang }: Props) => {
  const [isHovered, setIsHovered] = useState(true);
  const [selectedOption, setSelectedOption] = useState<CheckboxType>("products");
  const [query, setQuery] = useState("");
  const [foundItems, setFoundItems] = useState<any[]>([]);

  useDebounceEffect(
    () => {
      if (!query) return;
      search(`${selectedOption}/search?q=${query}`).then((results) => setFoundItems(results));
    },
    300,
    [query]
  );

  return (
    <div className="relative" onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(true)}>
      <button
        className={cn(
          "pl-2 text-primary-bright transition-colors duration-250 text-[30px] flex items-center hover:cursor-pointer z-30",
          isHovered && "text-primary-bright-light cursor-pointer"
        )}
      >
        <IoSearch />
      </button>
      {isHovered && (
        <>
          {/* To allow mouse cursor to reach the search menu without menu collapsing onMouseLeave */}
          <div className="absolute top-0 left-0 -right-6 bottom-[-18px] z-20 cursor-pointer" />
          {/* Tail + Menu */}
          <div className="absolute bottom-[-18px] left-4 w-4 h-4 bg-gray-50 rotate-45 shadow-2xl" />
          <div className="absolute top-[115%] left-0 border-2 w-[300px] bg-gray-50 shadow-2xl h-auto max-h-[60vh] overflow-y-scroll">
            <div className="w-full p-4">
              <Heading size="sm" className="mb-3 uppercase tracking-wide mt-4">
                {`${dict[lang].title}:`}
              </Heading>
              <div className="flex items-center gap-4 mb-2">
                <RadioGroup
                  label={dict[lang].products.label}
                  name="searchIn"
                  value="products"
                  selectedOption={selectedOption}
                  setSelectedOption={setSelectedOption}
                />
                <RadioGroup label={dict[lang].news.label} name="searchIn" value="news" selectedOption={selectedOption} setSelectedOption={setSelectedOption} />
              </div>
              <input
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                className="custom-input w-full mb-6"
                placeholder={dict[lang][selectedOption].search}
              />
              <ul>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
                <li>Product one</li>
                <li>Product two</li>
              </ul>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default SearchMenu;
