"use client";

import { useLangContext } from "@/context";
import { cn } from "@/utils";
import React from "react";

const dict = {
  en: "Service is temporarily unavailable.",
  lv: "Pakalpojums šobrīd nav pieejams.",
};

type Props = {
  className?: string;
};

const ServiceUnavailable = ({ className }: Props) => {
  const { lang } = useLangContext();
  return <p className={cn("text-sm text-center", className)}>{dict[lang]}</p>;
};

export default ServiceUnavailable;
