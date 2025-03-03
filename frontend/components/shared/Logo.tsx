import Link from "next/link";
import React from "react";
import Image from "next/image";

import logo from "@/public/biedribas_logo.png";

type Props = {
  hasBackground?: boolean;
};

const Logo = ({ hasBackground = true }) => {
  return (
    <Link
      className={`${
        hasBackground ? "bg-light-nav" : ""
      } z-10 lg:h-24 shrink bg-opacity-80 backdrop-blur-nano rounded-full max-w-[133px] sm:max-w-[200px] w-full`}
      href="/"
    >
      <Image src={logo} alt="Biedrības logo" />
    </Link>
  );
};

export default Logo;
