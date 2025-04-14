import { Heading } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types";
import React from "react";

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  return (
    <>
      <Heading size="xl" className="">
        {"MAKE A PAYMENT"}
      </Heading>
      <p>{"ENter personal details and make a payment! "}</p>
    </>
  );
};

export default Page;
