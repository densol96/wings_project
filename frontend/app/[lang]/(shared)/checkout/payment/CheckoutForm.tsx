"use client";

import { FormEvent } from "react";
import { PaymentElement, useStripe, useElements } from "@stripe/react-stripe-js";
import toast from "react-hot-toast";
import { useLangContext } from "@/context";
import { Button, Heading, MyImage, OrderReview } from "@/components";
import { PaymentSectionDictionary } from "@/types";

type Props = {
  dict: PaymentSectionDictionary;
};

const CheckoutForm = ({ dict }: Props) => {
  const stripe = useStripe();
  const elements = useElements();

  const { lang } = useLangContext();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    if (!stripe || !elements) {
      return;
    }

    const { error } = await stripe.confirmPayment({
      elements,
      confirmParams: {
        return_url: `${window.location.origin}/${lang}/checkout/payment/result`,
      },
      redirect: "always",
    });

    if (error) {
      // например, card_decline, validation_error и т.д.
      console.log("STRIPE ERROR: ", error);
      toast.error(error.message ?? "Payment failed");
    } else {
      toast.success("Payment succeeded!");
    }
  };

  return (
    <>
      <Heading as="h1" size="xl">
        {dict.title}
      </Heading>
      <p className="mb-16">{dict.description}</p>
      <div className="grid md:grid-cols-[5fr_6fr] md:gap-32 gap-16 grid-cols-1">
        <div>
          <div className="relative overflow-hidden min-h-[185px] mb-10">
            <MyImage
              image={{
                src: "/payment.png",
                alt: dict.imageDescription,
              }}
            />
          </div>
          <OrderReview dict={dict.orderSummary} hidePayBtn={true} />
        </div>

        <form onSubmit={handleSubmit}>
          <PaymentElement />
          <Button color="green" disabled={!stripe} className="mt-10 rounded mx-auto block w-full">
            {dict.payNow}
          </Button>
        </form>
      </div>
    </>
  );
};

export default CheckoutForm;
