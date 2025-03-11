import { PagePropsWithId, SingleNewsItem } from "@/@types/shared";
import { fetcher } from "@/utils";
import React from "react";

export default async function Page({ params: { id, lang } }: PagePropsWithId) {
  const itemData = await fetcher<SingleNewsItem>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/eventss/${id}?lang=${lang}`);
  console.log(itemData);
  return <p>Hello world!</p>;
}
