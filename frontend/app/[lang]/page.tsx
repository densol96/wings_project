import { Wallpaper } from "@/components";
import RandomProducts from "@/components/RandomProducts";
import { HomeContent } from "@/components/Wallpaper";
import { getDictionary } from "@/dictionaries/dictionaries";

type Props = { params: { lang: string } };

export default async function Home({ params: { lang } }: Props) {
  const dict = await getDictionary(lang);
  const homeDictionary: HomeContent = dict.home;

  return (
    <>
      <Wallpaper homeDictionary={homeDictionary} />
      <RandomProducts />
      <section className="px-6 py-12 bg-white relative">
        <blockquote className="text-center font-semibold text-gray-600 w-[90%] lg:w-[50%] mx-auto">
          <p className="text-xl lg:text-2xl">“{homeDictionary.quote}”</p>
          <p className="mt-6 text-center text-base font-semibold">{homeDictionary.title}</p>
        </blockquote>
      </section>
    </>
  );
}
