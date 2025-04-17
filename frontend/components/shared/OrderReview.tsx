"use client";

import { useCartContext, useLangContext } from "@/context";
import { useCheckoutContext } from "@/context/CheckoutContext";
import { useRouter } from "next/navigation";
import React from "react";
import { Button, Heading, Spinner } from "../ui";
import { formatPrice } from "@/utils";
import { OrderSummaryDictionary } from "@/types";

type Props = {
  hidePayBtn?: boolean;
  dict: OrderSummaryDictionary;
};

const OrderReview = ({ hidePayBtn = false, dict }: Props) => {
  const { getTotalPrice, cartIsLoaded, items } = useCartContext();
  const { selectedDeliveryMethod, coupon, createIndent, isLoading } = useCheckoutContext();
  const { lang } = useLangContext();
  const router = useRouter();

  if (!cartIsLoaded) return null;
  return (
    <div className="">
      <Heading as="h2" size="sm" className="uppercase tracking-widest font-bold text-gray-700">
        {dict.yourOrder}
      </Heading>
      <div className="mt-4">
        {items.map((item) => (
          <div className="flex justify-between mt-1" key={item.title}>
            <p>
              {item.title} X <span>{item.quantity}</span>
            </p>
            <p>{formatPrice(item.price * item.quantity)}</p>
          </div>
        ))}
      </div>

      <div className="flex items-center justify-between mt-6 mb-4">
        <p>{dict.delivery}</p>
        <p className="font-bold">{formatPrice(selectedDeliveryMethod?.price || 0)}</p>
      </div>
      {coupon && (
        <div className="flex items-center justify-between mt-6 mb-4">
          <p>{dict.discount}</p>
          <p>{formatPrice(coupon.discount)}</p>
        </div>
      )}
      <div className="mt-4 border-t-1 border-gray-300 flex justify-between items-center pt-2">
        <p className="uppercase font-medium">{dict.total}</p>
        <p className="font-bold">{formatPrice(getTotalPrice() + (selectedDeliveryMethod?.price || 0) - (coupon?.discount || 0))}</p>
      </div>
      <div>
        {!hidePayBtn && (
          <Button disabled={isLoading} color="green" onClick={createIndent} className="w-full mt-4">
            {isLoading ? <Spinner color="white" /> : dict.proceedToPayment}
          </Button>
        )}
        <Button color="primary" onClick={() => router.push(`/${lang}/checkout`)} className="w-full mt-4">
          {dict.backToCart}
        </Button>
      </div>
    </div>
  );
};

export default OrderReview;
