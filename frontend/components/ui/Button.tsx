"use client";

import { ButtonProps } from "@/types";
import { cn } from "@/utils";

const sizes = {
  sm: "px-2",
  md: "py-2 px-6 rounded-xl font-medium",
  lg: "py-4 px-12 rounded-2xl font-bold text-lg",
} as const;

const colors = {
  primary: "bg-primary-bright text-gray-50 hover:bg-primary-bright-light disabled:bg-primary-bright",
  transparent: "",
  green: "bg-green-500 text-gray-50 hover:bg-green-700 font-medium",
} as const;

const Button = ({ children, size = "md", color = "primary", className, onClick, disabled = false, type = "submit" }: ButtonProps) => {
  return (
    <button disabled={disabled} onClick={onClick} className={cn("transition duration-250 disabled:cursor-not-allowed", colors[color], sizes[size], className)}>
      {children}
    </button>
  );
};

export default Button;
