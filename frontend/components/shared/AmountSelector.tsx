import React from "react";
import { Button } from "../ui";
import { FaMinus, FaPlus } from "react-icons/fa";
import { cn } from "@/utils";

type Props = {
  selectedAmount: number | null;
  incr: () => void;
  decr: () => void;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  inStockAmount: number;
  className?: string;
};

const AmountSelector = ({ selectedAmount, incr, decr, handleChange, inStockAmount, className }: Props) => {
  return (
    <div className={cn("border-1 border-gray-400 flex", className)}>
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
        className="custom-number-input w-14 outline-none text-center"
        type="number"
      />
      <Button onClick={incr} color="transparent" size="sm" className="border-l-1 border-gray-400 h-full" disabled={selectedAmount === inStockAmount}>
        <FaPlus />
      </Button>
    </div>
  );
};

export default AmountSelector;
