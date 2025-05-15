"use client";

import React from "react";
import Button from "./Button";
import { useFormStatus } from "react-dom";
import { ButtonProps } from "@/types";
import Spinner from "./Spinner";
import { useRouter } from "next/navigation";

const BackButton = ({ children, size = "md", color = "primary", className, onClick, disabled = false, type = "submit", isPending = false }: ButtonProps) => {
  const router = useRouter();
  return (
    <Button size={size} color={color} className={className} onClick={() => router.back()} type="button">
      {children || "Atgriezties atpakaļ"}
    </Button>
  );
};

export default BackButton;
