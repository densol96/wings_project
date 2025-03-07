"use client";

import { useEffect, useState } from "react";
import { PageInfo, ShortNewsItem } from "@/@types/shared";
import NewsItem from "./NewsItem";
import { Button } from "@/components/shared";

type NewsState = {
  news: ShortNewsItem[];
  pageable: PageInfo;
};

const NewsGrid = function () {
  const [{ news, pageable }, setNews] = useState<NewsState>({
    news: [],
    pageable: { size: 0, number: 0, totalElements: 0, totalPages: 0 },
  });
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [currentPage, setCurrentPage] = useState(1);

  async function getNews() {
    !isLoading && setIsLoading(true);
    error && setError(null);
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/api/events?page=${currentPage}`);
      if (!response.ok) throw new Error("No any news to display at this time.");
      const result = (await response.json()).result;
      setNews({
        news: [...news, ...result.content],
        pageable: result.page,
      });
    } catch (e) {
      setError(e instanceof Error ? e.message : "Service unavailable");
    } finally {
      setIsLoading(false);
    }
  }

  function handleClick() {
    setCurrentPage((cp) => cp + 1);
  }

  useEffect(() => {
    getNews();
  }, [currentPage]);

  console.log(JSON.stringify(pageable));

  if (isLoading) return <p>Loading...</p>;
  if (error || !news.length) return <p>{error}</p>;

  return (
    <>
      <div className="grid grid-cols-4 gap-8 mt-20">
        {news.map((item) => (
          <NewsItem key={item.id + "_" + item.title} item={item} />
        ))}
      </div>
      {currentPage < pageable.totalPages && (
        <Button onClick={handleClick} size="lg" className="mx-auto block mt-20">
          Citas ziņas
        </Button>
      )}
    </>
  );
};

export default NewsGrid;
