import React from "react";
import { getDictionary } from "@/dictionaries/dictionaries";
import { fetcher, formatDate } from "@/utils";

import { PagePropsWithId, SingleNewsItem } from "@/types";
import { Gallery, Heading, MyImage } from "@/components";

export const revalidate = 0;

type NewsItemDictionary = {
  startDate: string;
  endDate: string;
  postedOn: string;
  location: string;
};

// export const generateMetadata = async ({ params: { lang } }: PageProps) => {
//   const dict: NewsDictionaryType = (await getDictionary(lang)).news;
//   return {
//     title: dict.title,
//   };
// };

export default async function Page({ params: { id, lang } }: PagePropsWithId) {
  const dict: NewsItemDictionary = (await getDictionary(lang)).newsItem;
  const event = await fetcher<SingleNewsItem>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/events/${id}?lang=${lang}`);
  return (
    <div className="md:block flex md:flex-row flex-col">
      <div className="min-h-[400px] relative overflow-hidden rounded-md shadow-lg lg:w-[50%] lg:float-right lg:ml-16 my-10 w-full">
        {event.imageDtos.length <= 0 ? <MyImage /> : <Gallery images={event.imageDtos} />}
        {event.imageDtos.length > 1 && (
          <span className="pointer-events-none absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 p-6 bg-slate-100 rounded-lg opacity-60 font-bold text-xl group-hover:opacity-90 transition-opacity">
            {`1/${event.imageDtos.length}`}
          </span>
        )}
      </div>
      <article className="flex-col items-start gap-2">
        <div className="lg:block sm:flex flex-row block justify-between items-center">
          <header>
            <Heading size="xl">{event.translationDto.title}</Heading>
          </header>
          <div className="mb-6 flex gap-8 lg:mb-8">
            {event.startDate && (
              <div className="flex flex-col gap-2">
                <span className="font-medium">{dict.startDate}:</span>
                <span className="text-gray-500">{formatDate(event.startDate)}</span>
              </div>
            )}
            {event.endDate && (
              <div className="flex flex-col gap-2">
                <span className="font-medium">{dict.endDate}:</span>
                <span className="text-gray-500">{formatDate(event.endDate)}</span>
              </div>
            )}
            <div className="flex flex-col gap-2">
              <span className="font-medium">{dict.postedOn}:</span>
              <span className="text-gray-500">{formatDate(event.createdAt)}</span>
            </div>
            {event.translationDto.location && (
              <div className="flex flex-col gap-2">
                <span className="font-medium">{dict.location}:</span>
                <span className="text-gray-500">{event.translationDto.location}</span>
              </div>
            )}
          </div>
        </div>

        <div className="mb-8 mt-8 h-[0.3px] lg:w-[45%] w-full bg-gray-700 opacity-50"></div>
        <p>{event.translationDto.description}</p>
      </article>
    </div>
  );
}
