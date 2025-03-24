import { cn } from "@/utils";
import React from "react";
import { IoMdClose } from "react-icons/io";

type Props = {
  className?: string;
};

const CloseBtn = ({ className }: Props) => {
  return (
    <button className={cn("text-gray-300 hover:text-gray-50 text-6xl font-bold transition duration-300 md:hidden", className)}>
      <IoMdClose />
    </button>
  );
};

export default CloseBtn;
