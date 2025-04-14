"use client";

import React from "react";
import { useRouter } from "next/navigation";
import { useCartContext, useLangContext } from "@/context";
import CartItemInfo from "./CartItemInfo";
import { CartDeliveryRequiredTranslation } from "@/types";
import { Button, Spinner } from "@/components";

type Props = {
  translations: {
    emptyCart: string;
    continueShopping: string;
    clearCart: string;
    isAvailable: string;
    total: string;
    price: string;
  };
};

const CartSummary = ({ translations }: Props) => {
  const { items, cartIsLoaded, clearCart } = useCartContext();
  const { lang } = useLangContext();
  const router = useRouter();

  const cartContent =
    items.length === 0 ? (
      <div>
        <p className="mb-10">{translations.emptyCart}</p>
        <Button onClick={() => router.push(`/${lang}/shop`)} color="green">
          {translations.continueShopping}
        </Button>
      </div>
    ) : (
      <div>
        <ul className="flex flex-col gap-4 max-h-[580px] overflow-y-auto">
          {items.map((item) => (
            <CartItemInfo translations={translations} key={item.id + "_" + item.title} product={item} />
          ))}
        </ul>
        <div className="mt-10 flex gap-4">
          <Button onClick={() => router.push(`/${lang}/shop`)} color="green">
            {translations.continueShopping}
          </Button>
          <Button onClick={clearCart}>{translations.clearCart}</Button>
        </div>
      </div>
    );

  return cartIsLoaded ? cartContent : <Spinner className="justify-end" />;
};

export default CartSummary;
