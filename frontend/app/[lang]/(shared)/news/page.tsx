import { NewsDictionaryType, PageProps } from "@/@types/shared";
import { Heading } from "@/components/shared";
import { getDictionary } from "@/dictionaries/dictionaries";
import NewsGrid from "./NewsGrid";

export const generateMetadata = async ({ params: { lang } }: PageProps) => {
  const dict: NewsDictionaryType = (await getDictionary(lang)).news;
  return {
    title: dict.title,
  };
};

const News = async function ({ params: { lang } }: PageProps) {
  const dict: NewsDictionaryType = (await getDictionary(lang)).news;

  return (
    <>
      <Heading size="xl" className="text-center">
        {dict.title}
      </Heading>
      <p className="mb-6 sm:text-left text-center">{dict.description}</p>
      <NewsGrid dict={dict} lang={lang} />
    </>
  );
};

export default News;
