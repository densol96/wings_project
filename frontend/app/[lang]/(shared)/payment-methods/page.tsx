import { Heading, InfoPageTemplate } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types";
import React from "react";

const Page = async ({ params: { lang } }: PageProps) => {
  return <InfoPageTemplate subject={(await getDictionary(lang)).paymentMethods} />;
};

export default Page;
