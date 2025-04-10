"use client";

import { useCartContext, useLangContext } from "@/context";
import Link from "next/link";
import React from "react";
import { CartIcon } from "../ui";
import { isClient } from "@/utils";

type Props = {};

const CartIconLink = ({}: Props) => {
  const { lang } = useLangContext();
  const { items, cartIsLoaded, getTotalNumberOfProducts } = useCartContext();

  return (
    <Link className="relative" href={`/${lang}/checkout`}>
      <CartIcon />
      {
        <p className="absolute top-[-10px] rounded-full bg-yellow-900 w-[25px] h-[25px] text-gray-50 font-bold flex items-center justify-center text-base">
          {cartIsLoaded && getTotalNumberOfProducts()}
        </p>
      }
    </Link>
  );
};

export default CartIconLink;
