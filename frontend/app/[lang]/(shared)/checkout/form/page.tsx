import { Heading, OrderReview } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps, PersonalInfoSectionDictionary } from "@/types";
import React from "react";

import PersonalInfoForm from "./PersonalInfoForm";

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  const pageDict: PersonalInfoSectionDictionary = dict.checkout.personalInfo;

  return (
    <>
      <Heading size="xl" className="">
        {pageDict.title}
      </Heading>
      <p>{pageDict.description}</p>
      <div className="grid lg:grid-cols-[6fr_3fr] grid-cols-1 gap-x-16 mt-10">
        <PersonalInfoForm dict={pageDict.form} />
        <OrderReview dict={dict.checkout.orderSummary} />
      </div>
    </>
  );
};

export default Page;
