"use client";

import React, { useState } from "react";
import { Button, Spinner } from "@/components";
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
        <div className="border-1 border-gray-400 flex">
          <Button
            onClick={decr}
            color="transparent"
            size="sm"
            className="border-r-1 border-gray-400 h-full"
            disabled={selectedAmount === null || selectedAmount === 1}
          >
            <FaMinus />
          </Button>
          <input
            value={selectedAmount === null ? "" : selectedAmount}
            onChange={handleChange}
            className="custom-number-input py-2 w-14 outline-none text-center"
            type="number"
          />
          <Button
            onClick={incr}
            color="transparent"
            size="sm"
            className="border-l-1 border-gray-400 h-full"
            disabled={selectedAmount === product.inStockAmount}
          >
            <FaPlus />
          </Button>
        </div>
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
