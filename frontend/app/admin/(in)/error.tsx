"use client";
import React from "react";
import { useRouter } from "next/navigation";

import { basicErrorText } from "@/utils";
import DashboardButton from "@/components/ui/DashboardButton";

type Props = {
  error: Error;
  reset: () => void;
};

const Error = ({ error, reset }: Props) => {
  const router = useRouter();
  return (
    <blockquote className="text-center font-bold w-[90%] lg:w-[50%] absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
      <p className="text-xl lg:text-2xl text-center">{error.message || basicErrorText()}</p>
      <DashboardButton />
    </blockquote>
  );
};

export default Error;
