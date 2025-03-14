import React from "react";

import { getDictionary } from "@/dictionaries/dictionaries";

import { PageProps } from "@/types";
import { Heading } from "@/components";

type ContactDetail = {
  label: string;
  info: string;
};

type ScheduleItem = {
  day: string;
  hours: string;
};

type Dictionary = {
  title: string;
  description: string;
  contactDetails: ContactDetail[];
  schedule: ScheduleItem[];
  contactsName: string;
  openTitle: string;
  mapDescription: string;
};

export const generateMetadata = async ({ params }: PageProps) => {
  const dict: Dictionary = (await getDictionary(params.lang)).contacts;
  return {
    title: dict.contactsName,
  };
};

const Page = async ({ params: { lang } }: PageProps) => {
  const dict: Dictionary = (await getDictionary(lang)).contacts;

  return (
    <>
      <Heading size="xl" className="text-center">
        {dict.title}
      </Heading>
      <p className="">{dict.description}</p>

      <div className="grid sm:grid-cols-2 grid-cols-1 md:gap-20 gap-16 mt-20">
        <div className="col-start-1 min-w-auto">
          <Heading size="lg" as="h2" className="text-center mb-6">
            {dict.contactsName}
          </Heading>
          <div className="flex flex-col gap-10">
            {dict.contactDetails.map(({ label, info }, index) => (
              <div key={index + "contactDetails"} className="flex justify-between items-center text-lg text-gray-800">
                <span className="font-medium">{label}</span>
                <span className="text-gray-500">{info}</span>
              </div>
            ))}
          </div>
        </div>

        <div className="md:col-start-1 sm:col-start-2 col-start-1">
          <Heading size="lg" as="h2" className="text-center mb-6">
            {dict.openTitle}
          </Heading>
          <div className="flex flex-col gap-4">
            {dict.schedule.map(({ day, hours }, index) => (
              <div key={index + "openTitle"} className="flex justify-between items-center text-lg">
                <span className="font-medium text-gray-800">{day}</span>
                <span className="text-gray-600">{hours}</span>
              </div>
            ))}
          </div>
        </div>

        <div className="md:row-span-2 md:row-start-1 md:col-start-2 sm:col-span-2 min-h-[300px]">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d903.8545655142859!2d21.565869157205608!3d57.39756217516992!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46f1c8f04408f47f%3A0x7ddbf7d5f705b66e!2sSkolas%20iela%203%2C%20Ventspils%2C%20LV-3601!5e0!3m2!1slv!2slv!4v1737946025006!5m2!1slv!2slv"
            className="w-full h-full"
            loading="lazy"
            referrerPolicy="no-referrer-when-downgrade"
            aria-label={dict.mapDescription}
          />
        </div>
      </div>
    </>
  );
};

export default Page;
