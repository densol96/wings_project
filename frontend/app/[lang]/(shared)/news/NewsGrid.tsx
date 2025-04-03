"use client";

import { useEffect, useRef, useState } from "react";
import { parsePageableResponse } from "@/utils";

import { Lang, Locale, NewsDictionaryType, PageInfo, ShortNewsItem } from "@/types";

import { Button, Spinner } from "@/components";
import NewsItem from "./NewsItem";

type NewsState = {
  news: ShortNewsItem[];
  pageable: PageInfo;
};

type Props = {
  lang: Locale;
  dict: NewsDictionaryType;
};

const NewsGrid = function ({ lang, dict }: Props) {
  const [{ news, pageable }, setNews] = useState<NewsState>({
    news: [],
    pageable: { size: 0, page: 0, totalElements: 0, totalPages: 0 },
  });
  const [isError, setIsError] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [currentPage, setCurrentPage] = useState(1);

  // in Next.js in a dev mode, Strict mode is activated (effects run twice) and with [...state, ...news] this will be a bit of a problem, so pevent this with a ref
  const isFirstRender = useRef(true);

  async function getNews() {
    if (isFirstRender.current) {
      isFirstRender.current = false;
      return;
    }

    if (currentPage === 1) setIsLoading(true);
    setIsError(false);
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/events?page=${currentPage}&lang=${lang}`);
      if (!response.ok) throw new Error("response.ok = false likely due to problems with network (is server up?)");
      const result = await response.json();
      const { content, page, size, totalPages, totalElements } = parsePageableResponse<ShortNewsItem>(result);
      setNews((prev) => ({
        news: [...prev.news, ...content],
        pageable: { page, size, totalPages, totalElements },
      }));
    } catch (e) {
      console.error(e);
      setIsError(true);
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {
    getNews();
  }, [currentPage]);

  if (isLoading) return <Spinner className="mt-20" />;
  if (isError || !news.length) return <p className="mt-20 text-center text-xl">{dict.error}</p>;

  return (
    <>
      <div className="grid justify-center grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 mt-20">
        {news.map((item) => (
          <NewsItem key={item.id + "_" + item.translation.title} item={item} dict={dict} lang={lang} />
        ))}
      </div>
      {currentPage < pageable.totalPages && (
        <Button onClick={() => setCurrentPage((cp) => cp + 1)} size="lg" className="mx-auto block mt-20">
          {dict.button}
        </Button>
      )}
    </>
  );
};

export default NewsGrid;
