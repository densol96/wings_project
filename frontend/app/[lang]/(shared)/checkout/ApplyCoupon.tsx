"use client";

import { Button } from "@/components";
import { useCartContext, useLangContext } from "@/context";
import { useCheckoutContext } from "@/context/CheckoutContext";
import { useRequestAction } from "@/hooks";
import { Coupon } from "@/types";
import { formatPrice } from "@/utils";
import React, { useEffect, useState } from "react";
import toast from "react-hot-toast";

type Props = {
  translations: {
    useCoupon: string;
    applyCoupon: string;
    discount: string;
    enterDescription: string;
    explanation: string;
  };
};

const ApplyCoupon = ({ translations: { useCoupon, applyCoupon, discount: discountLabel, enterDescription, explanation } }: Props) => {
  const [couponCode, setCouponCode] = useState("");
  const { getTotalPrice } = useCartContext();
  const { coupon, setCoupon } = useCheckoutContext();

  const totalPrice = getTotalPrice();

  const {
    response,
    isLoading,
    error,
    request: makeRequest,
  } = useRequestAction<{ discount: number }>({
    endpoint: "coupons/calc",
    queryParams: {
      code: couponCode,
      total: `${totalPrice}`,
    },
  });

  useEffect(() => {
    coupon && makeRequest();
  }, [totalPrice]);

  useEffect(() => {
    if (response) setCoupon({ discount: response.discount, code: couponCode });
    if (error) {
      toast.error(error.message);
      setCoupon(undefined);
    }
  }, [response, error]);

  if (!coupon)
    return (
      <div className="mt-4">
        <p className="mb-2">{useCoupon}</p>
        <form
          className="flex  gap-2"
          onSubmit={(e) => {
            e.preventDefault();
            makeRequest();
          }}
        >
          <input value={couponCode} onChange={(e) => setCouponCode(e.target.value)} className="custom-input w-full" placeholder={enterDescription} />
          <Button color="primary" className="shrink-0">
            {applyCoupon}
          </Button>
        </form>
        <p className="text-sm mt-2 italic">{explanation}</p>
      </div>
    );

  return (
    <div className="flex items-center justify-between mt-4">
      <p>{discountLabel}</p>
      <p className="font-bold">-{formatPrice(coupon.discount)}</p>
    </div>
  );
};

export default ApplyCoupon;
