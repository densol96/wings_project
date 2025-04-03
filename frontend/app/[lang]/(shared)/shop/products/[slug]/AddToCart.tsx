"use client";

import React, { useState } from "react";
import { Button } from "@/components";
import { FaPlus, FaMinus } from "react-icons/fa6";
import { cn } from "@/utils";

type Props = {
  amountTotal: number;
  btnTitle: string;
  invalidAmountTitle: string;
};

const AddToCart = ({ amountTotal, btnTitle, invalidAmountTitle }: Props) => {
  const [selectedAmount, setSelectedAmount] = useState<number | null>(1);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (value === "") {
      setSelectedAmount(null);
    } else {
      const num = Number(value);
      if (!isNaN(num)) {
        setSelectedAmount(Math.min(amountTotal, Math.max(1, num)));
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
        return curValue < amountTotal ? curValue + 1 : amountTotal;
      });
    } else {
      setSelectedAmount(1);
    }
  };

  const addToCartIsDisabled = selectedAmount === null;

  return (
    amountTotal > 0 && (
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
          <Button onClick={incr} color="transparent" size="sm" className="border-l-1 border-gray-400 h-full" disabled={selectedAmount === amountTotal}>
            <FaPlus />
          </Button>
        </div>
        <Button disabled={addToCartIsDisabled} onClick={() => alert("Grozs un pirkumi tiek pārstrādāti..")}>
          {btnTitle}
        </Button>
        {addToCartIsDisabled && <p className="self-center text-red-700 font-medium">{invalidAmountTitle}</p>}
      </div>
    )
  );
};

export default AddToCart;
