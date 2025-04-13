"use client";

import React, { useState } from "react";
import { useDebounceEffect } from "@/hooks";
import { search } from "@/actions/search";

import SearchedProductItem from "./SearchedProductItem";
import SearchedNewsItem from "./SearchedNewsItem";
import { Heading, RadioGroup } from "../ui";

import { SearchedProductDto, SearchedNewsDto } from "@/types";
import { useLangContext } from "@/context/LangContext";
import { cn } from "@/utils";

type Props = {
  className?: string;
  groupName: string;
};

type CheckboxType = "news" | "products";

type SearchResultMap = {
  products: SearchedProductDto[];
  news: SearchedNewsDto[];
};

const searchUrls = {
  products: "products",
  news: "events",
} satisfies Record<CheckboxType, string>;

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
    postedOn: "Posted on",
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
    postedOn: "Publicēts",
  },
};

const SearchForm = ({ className, groupName }: Props) => {
  const [selectedOption, setSelectedOption] = useState<CheckboxType>("products");
  const [query, setQuery] = useState("");
  const [foundItems, setFoundItems] = useState<SearchResultMap[CheckboxType]>([]);
  const { lang } = useLangContext();

  const onCategoryChange = (newCategoryName: CheckboxType) => {
    setSelectedOption(newCategoryName);
    setFoundItems([]);
  };

  useDebounceEffect(
    () => {
      if (!query) {
        setFoundItems([]);
        return;
      }
      const url = `${searchUrls[selectedOption]}/search?q=${query}&lang=${lang}`;
      if (selectedOption === "products") search<SearchedProductDto>(url).then((results) => setFoundItems(results));
      else if (selectedOption === "news") search<SearchedNewsDto>(url).then((results) => setFoundItems(results));
    },
    300, // ms
    [query, selectedOption]
  );

  return (
    <div className={cn("w-full p-4", className)}>
      <Heading size="xs" className="mb-3 uppercase tracking-wide mt-4 font-medium text-center">
        {`${dict[lang].title}:`}
      </Heading>
      <div className="flex items-center justify-center gap-6 mb-2">
        <RadioGroup label={dict[lang].products.label} name={groupName} value="products" selectedOption={selectedOption} setSelectedOption={onCategoryChange} />
        <RadioGroup label={dict[lang].news.label} name={groupName} value="news" selectedOption={selectedOption} setSelectedOption={onCategoryChange} />
      </div>
      <input value={query} onChange={(e) => setQuery(e.target.value)} className="custom-input w-full mb-6" placeholder={dict[lang][selectedOption].search} />
      <ul className="flex flex-col gap-3 max-h-[40vh] overflow-y-scroll">
        {selectedOption === "products" &&
          foundItems.map((item) => <SearchedProductItem key={item.id + "_" + item.title} product={item as SearchedProductDto} lang={lang} query={query} />)}
        {selectedOption === "news" &&
          foundItems.map((item) => <SearchedNewsItem key={item.id + "_" + item.title} event={item as SearchedNewsDto} lang={lang} query={query} />)}
      </ul>
    </div>
  );
};

export default SearchForm;
