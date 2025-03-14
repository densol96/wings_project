import { getDictionary } from "@/dictionaries/dictionaries";
import { Locale, PageProps, PagePropsWithSlug } from "@/types";
import { Heading } from "@/components";
import Link from "next/link";
import { extractIdFromSlug, fetcher, slugify } from "@/utils";
import { notFound } from "next/navigation";

type SearchParams = {
  page?: string | number;
};

type Props = PagePropsWithSlug & {
  searchParams: SearchParams;
};

export const generateMetadata = async ({ params }: PageProps) => {
  const dict = (await getDictionary(params.lang)).about;
  return {
    title: dict.title,
  };
};

export const revalidate = 0;

const News = async function ({ params: { lang, slug }, searchParams }: Props) {
  const page = searchParams.page ? +searchParams.page : 1;
  return <p>HELLO WORLD!</p>;
};

export default News;
