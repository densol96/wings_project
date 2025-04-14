import { Heading } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types";
import React from "react";
import { StripeWrapper } from "./StripeWrapper";
import { CheckoutForm } from "./CheckoutForm";

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  return (
    <>
      <Heading size="xl" className="">
        {"MAKE A PAYMENT"}
      </Heading>
      <p>{"ENter personal details and make a payment! "}</p>
      <StripeWrapper>
        <div className="flex justify-center items-center min-h-screen">
          <CheckoutForm />
        </div>
      </StripeWrapper>
    </>
  );
};

export default Page;
