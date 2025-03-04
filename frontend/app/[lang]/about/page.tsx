import React from "react";
import { getDictionary } from "@/dictionaries/dictionaries";
import Image from "next/image";
import image from "@/public/assets/about_sparni.png";

const Page = async ({ params: { lang } }: { params: { lang: "lv" | "en" } }) => {
  const dict = await getDictionary(lang);
  const { title, description, mission, invitation } = dict.about;

  return (
    <section className="w-full lg:pl-48 px-4 md:px-5 mx-auto py-32 relative">
      <div className="w-full flex flex-col lg:flex-row gap-[100px] relative">
        <div className="w-full lg:w-[35%]">
          <h2 className="text-4xl font-bold font-manrope leading-normal lg:text-start text-center mb-5">{title}</h2>
          <div className="text-gray-500 text-base font-normal lg:text-start leading-loose flex flex-col gap-3">
            <p>{description}</p>
            <p>{mission}</p>
            <p>{invitation}</p>
          </div>
        </div>
        <div className="flex-1 relative h-auto">
          <Image src={image} alt="Biedriba Sparni kopa" className="lg:hidden w-full sm:w-[70%] mx-auto" />
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
