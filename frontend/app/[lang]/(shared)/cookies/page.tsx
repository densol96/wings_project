import { Heading } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { Locale, PageProps } from "@/types";
import React from "react";

type CookiesDict = {
  pageTitle: string;
  lastUpdated: string;
  sections: {
    title: string;
    content: string;
  }[];
};

const Page = async ({ params: { lang } }: PageProps) => {
  const dict: CookiesDict = (await getDictionary(lang)).cookiesPolicy;
  return (
    <>
      <Heading size="xl">{dict.pageTitle}</Heading>
      <p>{dict.lastUpdated}</p>
      {dict.sections.map((section) => {
        return (
          <section className="mt-10">
            <Heading size="md">{section.title}</Heading>
            <p>{section.content}</p>
          </section>
        );
      })}
    </>
  );
};

export default Page;
