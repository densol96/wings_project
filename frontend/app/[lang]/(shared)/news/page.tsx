import { PageProps } from "@/@types/shared";
import { Heading } from "@/components/shared";
import { getDictionary } from "@/dictionaries/dictionaries";
import NewsGrid from "./NewsGrid";

type SearchParams = {
  category?: string;
  page?: string | number;
};

type Props = PageProps & {
  searchParams: SearchParams;
};

const parseSearchParams = (searchParams: SearchParams) => {
  const category = searchParams.category || "all";
  const page = searchParams.page ? +searchParams.page : 1;
  return { category, page };
};

const News = async function ({ params: { lang }, searchParams }: Props) {
  const dict = await getDictionary(lang);
  const { category, page } = parseSearchParams(searchParams);

  return (
    <>
      <Heading size="xl" className="text-center">
        Jaunākās ziņas
      </Heading>
      <p className="mb-6">
        Uzziniet par mūsu jaunākajiem projektiem, pasākumiem un amatniecības izstādēm! Sekojiet līdzi jaunumiem un
        atklājiet unikālus rokdarbus, kas radīti ar mīlestību un rūpību. Pievienojieties mūsu aktivitātēm un atbalstiet
        sieviešu radošumu un izaugsmi!
      </p>
      <NewsGrid />
    </>
  );
};

export default News;
