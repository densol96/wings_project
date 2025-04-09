import { Locale, SearchedNewsDto } from "@/types";
import Link from "next/link";
import React from "react";
import { formatDate, highlightWithDiacritics } from "@/utils";
import { useRouter } from "next/navigation";

type Props = {
  event: SearchedNewsDto;
  lang: Locale;
  query: string;
};

const dict = {
  lv: "Publicēts",
  en: "Posted on",
};

const SearchedNewsItem = ({ event, lang, query }: Props) => {
  return (
    <Link href={`/${lang}/news/${event.id}`}>
      <li className="flex items-center gap-4 hover:bg-gray-300 p-2 transition-colors duration-300 text-sm rounded-md">
        <p>{highlightWithDiacritics(event.title, query)}</p>
        <p className="ml-auto text-xs self-start">
          {dict[lang]}: {formatDate(new Date(event.createdAt))}
        </p>
      </li>
    </Link>
  );
};

export default SearchedNewsItem;
