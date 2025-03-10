import React, { useState, CSSProperties } from "react";
import BeatLoader from "react-spinners/BeatLoader";

type Props = {
  className?: string;
  color?: "black" | "primary";
  size?: "md";
};

const override: CSSProperties = {};

export const Spinner = ({ className, color = "primary", size = "md" }: Props) => {
  const theme = color === "primary" ? "#751521" : color;
  const mapSizeInPx = {
    md: 20,
  } as const;

  return (
    <div className={`flex w-full justify-center ${className || ""}`}>
      <BeatLoader color={theme} cssOverride={override} size={mapSizeInPx[size]} />
    </div>
  );
};
