"use client";
import React from "react";
import { useRouter } from "next/navigation";

import { Button } from "@/components/ui";
import { basicErrorText } from "@/utils";

type Props = {
  error: Error;
  reset: () => void;
};

const Error = ({ error, reset }: Props) => {
  const router = useRouter();
  return (
    <blockquote className="text-center font-bold w-[90%] lg:w-[50%] absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
      <p className="text-xl lg:text-2xl text-center">{error.message || basicErrorText()}</p>
      <Button onClick={() => router.replace("/admin/orders")} className="mt-10">
        Uz mājām
      </Button>
    </blockquote>
  );
};

export default Error;
