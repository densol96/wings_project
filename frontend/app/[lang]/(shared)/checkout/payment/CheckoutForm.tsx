"use client";

import { FormEvent } from "react";
import { PaymentElement, useStripe, useElements } from "@stripe/react-stripe-js";
import toast from "react-hot-toast";
import { useLangContext } from "@/context";
import { Button, Heading, MyImage, OrderReview } from "@/components";
import { PaymentSectionDictionary } from "@/types";
import { useRouter } from "next/navigation";

type Props = {
  dict: PaymentSectionDictionary;
};

const CheckoutForm = ({ dict }: Props) => {
  const stripe = useStripe();
  const elements = useElements();

  const { lang } = useLangContext();
  const router = useRouter();

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
      if (error.code === "payment_intent_unexpected_state") {
        toast.error(
          lang == "lv"
            ? "Sesija ir beigusies. Lai turpinātu, lūdzu, sāciet maksājuma procesu no jauna."
            : "The session has ended. To continue, please restart the payment process."
        );
        setTimeout(() => {
          router.back();
        }, 3000);
      } else {
        toast.error(
          error.message ||
            (lang == "lv"
              ? "Maksājumu neizdevās pabeigt. Lūdzu, pārbaudiet savu maksājumu informāciju vai mēģiniet vēlreiz ar citu maksājuma metodi. Ja problēma turpinās, sazinieties ar mūsu atbalsta komandu."
              : "The payment could not be completed. Please check your payment details or try again using a different payment method. If the issue persists, contact our support team.")
        );
      }
    } else {
      toast.success(lang == "lv" ? "Maksājums veiksmīgi saņemts. Paldies par jūsu pirkumu!" : "Payment successfully received. Thank you for your purchase!");
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
