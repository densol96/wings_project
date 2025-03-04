import React from "react";
import Image from "next/image";
import image from "@/public/assets/about_sparni.png";
import { getDictionary } from "@/dictionaries/dictionaries";
import { Heading } from "@/components/shared";
import { PageProps } from "@/@types/shared";

export const generateMetadata = async ({ params }: PageProps) => {
  const dict: AboutDictionaryType = (await getDictionary(params.lang)).about;
  return {
    title: dict.title,
  };
};

type AboutDictionaryType = {
  title: string;
  description: string;
  mission: string;
  invitation: string;
  imageDescription: string;
};

const Page = async ({ params: { lang } }: PageProps) => {
  const dict: AboutDictionaryType = (await getDictionary(lang)).about;

  return (
    <section className="w-full lg:pl-48 px-4 md:px-5 mx-auto py-32 relative">
      <div className="w-full flex flex-col lg:flex-row gap-[100px] relative">
        <div className="w-full lg:w-[35%]">
          <Heading size="xl" className="lg:text-start text-center">
            {dict.title}
          </Heading>
          <div className="text-base font-normal lg:text-start leading-loose flex flex-col gap-3">
            <p>{dict.description}</p>
            <p>{dict.mission}</p>
            <p>{dict.invitation}</p>
          </div>
        </div>
        <div className="flex-1 relative h-auto">
          <Image src={image} alt={dict.imageDescription} className="lg:hidden w-full sm:w-[70%] mx-auto" />
        </div>
      </div>
      <div className="hidden lg:block absolute w-full h-full border-2 top-0 left-0 translate [clip-path:polygon(55%_0%,100%_0%,100%_100%,45%_100%)] overflow-hidden">
        <div
          className="h-full"
          style={{
            backgroundImage: "url('/assets/about_sparni.png')",
            backgroundPosition: "center left",
            backgroundSize: "cover",
            transform: "translateX(20%)",
          }}
        />
      </div>
    </section>
  );
};

export default Page;
