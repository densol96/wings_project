import { PageProps } from "@/@types/shared";
import { Heading } from "@/components/shared";
import { getDictionary } from "@/dictionaries/dictionaries";

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
        Veikals
      </Heading>
      <p className="mb-6">
        Uzziniet par mūsu jaunākajiem projektiem, pasākumiem un amatniecības izstādēm! Sekojiet līdzi jaunumiem un
        atklājiet unikālus rokdarbus, kas radīti ar mīlestību un rūpību. Pievienojieties mūsu aktivitātēm un atbalstiet
        sieviešu radošumu un izaugsmi!
      </p>
      <div className="grid grid-cols-[15rem_1fr] gap-8 mt-20">
        <div className="border-2 border-red-700">SIDEBAR</div>
        <div className="border-2 border-red-700">CONTENT</div>
      </div>
    </>
  );
};

export default News;
