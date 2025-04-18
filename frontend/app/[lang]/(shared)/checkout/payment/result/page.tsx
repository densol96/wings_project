import { Heading, MyImage } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { Locale, SummaryLabels } from "@/types";
import React from "react";
import PaymentInfo from "./PaymentInfo";
import { redirect } from "next/navigation";

type Props = {
  params: {
    lang: Locale;
  };
  searchParams: {
    payment_intent?: string;
    redirect_status?: string;
  };
};

type Content = {
  title: string;
  description: string;
  image: string;
};

type PaymentResultMessages = {
  success: Content;
  failure: Content;
  summary: SummaryLabels;
};

const Page = async ({ searchParams: { payment_intent, redirect_status }, params: { lang } }: Props) => {
  const dict: PaymentResultMessages = (await getDictionary(lang)).checkout.paymentResult;
  const isSuccess = redirect_status === "succeeded";
  const appropriateDict = dict[isSuccess ? "success" : "failure"];

  if (!redirect_status && !payment_intent) redirect(`/${lang}`);

  return (
    <div className="grid grid-cols-1 md:grid-cols-[500px_1fr] gap-10 w-[80%] mx-auto">
      <div className="relative min-h-[400px] w-[400px] rounded-full overflow-hidden mx-auto">
        <MyImage
          withEffect={false}
          image={{
            src: isSuccess ? "/payment-good.png" : "/payment-bad.png",
            alt: appropriateDict.image,
          }}
        />
      </div>
      <div className="text-center md:text-start flex-shrink">
        <Heading as="h1" size="xl">
          {appropriateDict.title}
        </Heading>
        <p className="mb-10">{appropriateDict.description}</p>
        {isSuccess && <PaymentInfo dict={dict.summary} paymentIntentId={payment_intent as string} />}
      </div>
    </div>
  );
};

export default Page;
