import { SearchForm } from "@/components";
import { PageProps } from "@/types";
import React from "react";

const Page = ({ params: { lang } }: PageProps) => {
  return (
    <>
      <SearchForm />
    </>
  );
};

export default Page;
