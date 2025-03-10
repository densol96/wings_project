import { Wallpaper, RandomProducts } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import Image from "next/image";
import lineOne from "@/public/line_1.png";
import { PageProps, HomeContent } from "@/@types/shared";

const Home = async function ({ params: { lang } }: PageProps) {
  const dict = await getDictionary(lang);
  const homeDictionary: HomeContent = dict.home;

  return (
    <>
      <Wallpaper lang={lang} homeDictionary={homeDictionary} />
      <RandomProducts />
      <section className="px-6 py-12 bg-white relative mb-40">
        <Image
          className="absolute sm:bottom-[-20%] lg:bottom-[0%] right-[15%] sm:w-[125px] lg:w-[200px] block w-[75px] bottom-0"
          src={lineOne}
          alt={homeDictionary.lineOneDescription}
        />
        <Image
          className="absolute lg:top-[-20%] sm:top-[-40%] top-[-15%] left-[5%] sm:left-[15%] lg:w-[200px] sm:w-[125px] w-[75px] rotate-[195deg] lg:rotate-180"
          src={lineOne}
          alt={homeDictionary.lineTwoDescription}
        />
        <blockquote className="text-center font-bold w-[90%] lg:w-[50%] mx-auto">
          <p className="text-xl lg:text-2xl">“{homeDictionary.quote}”</p>
          <p className="mt-6 text-primary-bright">{homeDictionary.title}</p>
        </blockquote>
      </section>
    </>
  );
};

export default Home;
