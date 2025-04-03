import { cn } from "@/utils";
import React from "react";

type Props = {
  className?: string;
  children: string;
  size: "tiny" | "xs" | "sm" | "md" | "lg" | "xl" | "2xl";
  as?: "h1" | "h2" | "h3" | "h4" | "h5" | "h6";
};

const Heading = ({ className, children, size = "xl", as = "h1" }: Props) => {
  const mapTagBySize = {
    tiny: "h6",
    xs: "h5",
    sm: "h4",
    md: "h3",
    lg: "h2",
    xl: "h1",
    "2xl": "h1",
  } as const;

  const mapClassNameBySize = {
    tiny: "text-sm",
    xs: "text-lg",
    sm: "text-xl",
    md: "text-2xl",
    lg: "text-3xl",
    xl: "text-4xl font-bold leading-normal mb-5",
    "2xl": "text-5xl sm:text-7xl font-bold tracking-tight",
  } as const;

  return React.createElement(as || mapTagBySize[size], { className: cn(mapClassNameBySize[size], className) }, children);
};

export default Heading;
