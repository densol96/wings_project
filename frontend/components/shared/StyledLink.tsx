import Link from "next/link";
import React from "react";
import { FaHandPointRight } from "react-icons/fa";

type Props = {
  className?: string;
  children?: React.ReactNode;
  href: string;
};

const StyledLink = ({ className, children, href }: Props) => {
  return (
    <Link
      className={`border-b border-primary-bright text-primary-bright hover:border-transparent transition-border duration-200 flex items-center gap-2 ${className}`}
      href={href}
    >
      <span>{children}</span>
      <FaHandPointRight />
    </Link>
  );
};

export default StyledLink;
