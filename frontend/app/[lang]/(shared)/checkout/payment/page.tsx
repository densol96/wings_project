import { Heading } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types";
import React from "react";

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  return (
    <>
      <Heading size="xl" className="">
        {requiredTranslations.title}
      </Heading>
      <p>{requiredTranslations.description}</p>
      <div className="grid grid-cols-[6fr_3fr] gap-x-16 mt-10">
        <CartSummary translations={pickLabels(requiredTranslations, ["emptyCart", "continueShopping", "clearCart", "isAvailable", "total", "price"])} />
        <DeliverySummary
          translations={pickLabels(requiredTranslations, ["cartTotals", "total", "delivery", "country", "countryList", "totalTogether", "proceedWithOrder"])}
        />
      </div>
    </>
  );
};

export default Page;
