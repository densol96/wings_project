"use client";

import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import { useCheckoutContext } from "@/context/CheckoutContext";

import CheckoutForm from "./CheckoutForm";
import { Button, Heading, MyImage, Spinner } from "@/components";
import { useLangContext } from "@/context";
import { PaymentSectionDictionary } from "@/types";
import { useRouter } from "next/navigation";

type Props = {
  dict: PaymentSectionDictionary;
};

const stripePromise = loadStripe(process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!);

const StripeElement = ({ dict }: Props) => {
  const { paymentIndentSecret, isLoading } = useCheckoutContext();
  const router = useRouter();
  const { lang } = useLangContext();

  if (isLoading) return <Spinner />;
  if (!paymentIndentSecret)
    return (
      <div className="flex flex-col gap-10 items-center">
        <div className="min-h-[300px] relative w-1/2 rounded-full overflow-hidden">
          <MyImage
            image={{
              src: "/payment-early.png",
              alt: dict.imageDescription,
            }}
          />
        </div>
        <Heading as="h1" size="xl" className="text-center md:text-4xl text-2xl">
          {dict.errorTitle}
        </Heading>
        <Button onClick={() => router.push(`/${lang}/checkout`)}>{dict.orderSummary.backToCart}</Button>
      </div>
    );

  const options = {
    clientSecret: paymentIndentSecret,
    appearance: {
      theme: "stripe",
      labels: "above",
      variables: {
        colorPrimary: "#751521",
        colorBackground: "#fbe9d07a",
        colorText: "#30313D",
        colorDanger: "#DF1B41",
        borderRadius: "8px",
        spacingUnit: "4px",
      },
    },
    locale: lang,
  };

  return (
    <Elements stripe={stripePromise} options={options}>
      <CheckoutForm dict={dict} />
    </Elements>
  );
};

export default StripeElement;
