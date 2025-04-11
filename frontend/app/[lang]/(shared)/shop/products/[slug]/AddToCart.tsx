"use client";

import React, { useState } from "react";
import { Button, Spinner, AmountSelector } from "@/components";
import { FaPlus, FaMinus } from "react-icons/fa6";
import { TiTick } from "react-icons/ti";
import { ProductData, useCartContext, useLangContext } from "@/context";
import Link from "next/link";

type Props = {
  product: ProductData;
  dict: {
    btnTitle: string;
    invalidAmountTitle: string;
    isAlreadyInCart: string;
  };
};

const AddToCart = ({ product, dict }: Props) => {
  const [selectedAmount, setSelectedAmount] = useState<number | null>(1);
  const { addProduct, productIsInCart, cartIsLoaded } = useCartContext();
  const { lang } = useLangContext();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (value === "") {
      setSelectedAmount(null);
    } else {
      const num = Number(value);
      if (!isNaN(num)) {
        setSelectedAmount(Math.min(product.inStockAmount, Math.max(1, num)));
      }
    }
  };

  const decr = () => {
    if (selectedAmount !== null && selectedAmount > 1) {
      setSelectedAmount((prev) => (prev as number) - 1);
    }
  };

  const incr = () => {
    if (selectedAmount !== null) {
      setSelectedAmount((prev) => {
        const curValue = prev as number;
        return curValue < product.inStockAmount ? curValue + 1 : product.inStockAmount;
      });
    } else {
      setSelectedAmount(1);
    }
  };

  const addToCartIsDisabled = selectedAmount === null;
  const alreadyInCart = productIsInCart(product.id);

  const forRender = alreadyInCart ? (
    <Link href={`/${lang}/checkout`}>
      <Button color="green" className="flex items-center gap-2">
        <TiTick /> {dict.isAlreadyInCart}
      </Button>
    </Link>
  ) : (
    product.inStockAmount > 0 && (
      <div className="flex gap-4 mt-2 flex-wrap">
        <AmountSelector
          className="min-h-[40px]"
          handleChange={handleChange}
          inStockAmount={product.inStockAmount}
          selectedAmount={selectedAmount}
          incr={incr}
          decr={decr}
        />
        <Button disabled={addToCartIsDisabled} onClick={() => addProduct(product, selectedAmount as number)}>
          {dict.btnTitle}
        </Button>
        {addToCartIsDisabled && <p className="self-center text-red-700 font-medium">{dict.invalidAmountTitle}</p>}
      </div>
    )
  );

  return <div className="mt-2">{cartIsLoaded ? forRender : <Spinner className="justify-start ml-5" size="sm" />}</div>;
};

export default AddToCart;
