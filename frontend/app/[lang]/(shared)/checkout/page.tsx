import React from "react";
import CartSummary from "./CartSummary";
import { getDictionary } from "@/dictionaries/dictionaries";
import { CartDeliveryRequiredTranslation, Locale, PageProps, SharedDict } from "@/types";
import { Heading } from "@/components";
import DeliverySummary from "./DeliverySummary";
import { pickLabels } from "@/utils";

type Props = {
  lang: Locale;
};

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  const requiredTranslations: CartDeliveryRequiredTranslation = {
    ...dict.shared,
    ...dict.checkout.cartDelivery,
  };

  return (
    <>
      <Heading size="xl" className="">
        {requiredTranslations.title}
      </Heading>
      <p>{requiredTranslations.description}</p>
      <div className="grid grid-cols-[6fr_3fr] gap-x-16 mt-10">
        <CartSummary translations={pickLabels(requiredTranslations, ["emptyCart", "continueShopping", "clearCart", "isAvailable", "total", "price"])} />
        <DeliverySummary
          translations={pickLabels(requiredTranslations, [
            "cartTotals",
            "total",
            "delivery",
            "country",
            "countryList",
            "totalTogether",
            "proceedWithOrder",
            "useCoupon",
            "applyCoupon",
            "discount",
            "enterDescription",
            "explanation",
          ])}
        />
      </div>
    </>
  );
};

export default Page;
