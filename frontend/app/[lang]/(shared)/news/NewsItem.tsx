import React from "react";
import Link from "next/link";
import Image from "next/image";
import { formatDate } from "@/utils";

import { Locale, NewsDictionaryType, ShortNewsItem } from "@/types";

import { Heading, StyledLink } from "@/components";

import MyImage from "@/components/ui/MyImage";

const NewsItem = function ({ item, lang, dict }: { item: ShortNewsItem; lang: Locale; dict: NewsDictionaryType }) {
  const description = item.translation?.description;
  return (
    <div className="flex flex-col shadow-md rounded-xl overflow-hidden hover:shadow-lg hover:-translate-y-1 transition-all duration-300 max-w-sm w-full mx-auto">
      <div className="h-auto overflow-hidden">
        <div className="h-44 overflow-hidden relative">
          <MyImage image={item.image} lang={lang} />
        </div>
      </div>
      <div className="bg-white py-4 px-6">
        <Heading size="xs" as="h3" className="font-medium">
          {item.translation?.title}
        </Heading>
        <p className="text-sm text-gray-500 break-words mt-2">{description?.length > 20 ? `${description.slice(0, 125)}...` : description}</p>
        <div className="flex sm:flex-row flex-col justify-between items-center mt-6">
          <p className="text-sm text-gray-500">
            {dict.postedOn} {formatDate(item.createdAt)}
          </p>
          <StyledLink className="mx-auto" href={`/${lang}/news/${item.id}`}>
            {dict.readMore}
          </StyledLink>
        </div>
      </div>
    </div>
  );
};

export default NewsItem;
