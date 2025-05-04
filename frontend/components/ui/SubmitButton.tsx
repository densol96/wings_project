"use client";

import React from "react";
import Button from "./Button";
import { useFormStatus } from "react-dom";
import { ButtonProps } from "@/types";
import Spinner from "./Spinner";

const SubmitButton = ({ children, size = "md", color = "primary", className, onClick, disabled = false, type = "submit", isPending = false }: ButtonProps) => {
  const { pending } = useFormStatus();
  return (
    <Button size={size} color={color} className={className} onClick={onClick} disabled={disabled || pending || isPending} type={type}>
      {pending || isPending ? <Spinner color="white" /> : children}
    </Button>
  );
};

export default SubmitButton;
