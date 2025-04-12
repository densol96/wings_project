import React from "react";
import CartSummary from "./CartSummary";
import { getDictionary } from "@/dictionaries/dictionaries";
import { CartDeliveryRequiredTranslation, CartDeliveryTranslation, Locale, PageProps, SharedDict } from "@/types";
import { Heading } from "@/components";
import DeliverySummary from "./DeliverySummary";

type Props = {
  lang: Locale;
};

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);

  const sharedDict: SharedDict = dict.shared;

  const requiredTranslations: CartDeliveryRequiredTranslation = {
    total: sharedDict.total,
    price: sharedDict.price,
    isAvailable: sharedDict.isAvailable,
    ...dict.checkout.cartDelivery,
  };

  return (
    <>
      <Heading size="xl" className="">
        {requiredTranslations.title}
      </Heading>
      <p>{requiredTranslations.description}</p>
      <div className="grid grid-cols-[6fr_3fr] gap-x-16 mt-10">
        <CartSummary translations={requiredTranslations} />
        <DeliverySummary />
      </div>
    </>
  );
};

export default Page;
