import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types";
import { redirect } from "next/navigation";
import React from "react";

type Props = {
  className?: string;
  children?: React.ReactNode;
};

const Page = async ({ params: { lang } }: PageProps) => {
  redirect((await getDictionary(lang)).shop.allCategoryRedirect);
};

export default Page;
