import { cn } from "@/utils";
import Link from "next/link";
import React from "react";
import { FaHandPointRight } from "react-icons/fa";

type Props = {
  className?: string;
  children?: React.ReactNode;
  href: string;
  showIcon?: boolean;
  innerClassName?: string;
};

const StyledLink = ({ className, children, href, showIcon = true, innerClassName }: Props) => {
  return (
    <Link
      className={cn(
        "border-b border-primary-bright text-primary-bright hover:border-transparent transition-border duration-200 flex items-center gap-2",
        className
      )}
      href={href}
    >
      <span className={cn("flex items-center gap-2", innerClassName)}>{children}</span>
      {showIcon && <FaHandPointRight />}
    </Link>
  );
};

export default StyledLink;
