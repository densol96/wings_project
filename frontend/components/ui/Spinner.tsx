import { cn } from "@/utils";
import React, { useState, CSSProperties } from "react";
import BeatLoader from "react-spinners/BeatLoader";

type Props = {
  className?: string;
  color?: "black" | "primary" | "white";
  size?: "md" | "sm";
};

const override: CSSProperties = {};

const Spinner = ({ className, color = "primary", size = "md" }: Props) => {
  const theme = color === "primary" ? "#751521" : color;
  const mapSizeInPx = {
    sm: 10,
    md: 20,
  } as const;

  return (
    <div className={cn("flex w-full justify-center", className)}>
      <BeatLoader color={theme} cssOverride={override} size={mapSizeInPx[size]} />
    </div>
  );
};

export default Spinner;
