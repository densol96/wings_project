"use client";

import React from "react";
import { usePathname } from "next/navigation";
import { FaFacebook, FaTelegram, FaSquareXTwitter } from "react-icons/fa6";

type Props = {};

const SocialLinks = ({}: Props) => {
  const pathname = usePathname();
  const fullUrl = `${process.env.NEXT_PUBLIC_FRONTEND_URL}${pathname}`;
  const title = pathname.startsWith("/en") ? "Share on" : "Dalīties";
  return (
    <div className="mt-10 flex gap-6 items-center">
      <p className="uppercase font-medium">{title}:</p>
      <div className="flex gap-3 ">
        <a href={`https://www.facebook.com/sharer.php?u=${fullUrl}`} target="_blank" rel="noopener noreferrer">
          <FaFacebook className="text-[#1877F2] hover:opacity-80 transition text-[35px]" />
        </a>
        <a href={`https://t.me/share/url?url=${fullUrl}`} target="_blank" rel="noopener noreferrer">
          <FaTelegram className="text-[#0088cc] hover:opacity-80 transition text-[35px]" />
        </a>
        <a href={`https://twitter.com/intent/tweet?url=${fullUrl}`} target="_blank" rel="noopener noreferrer">
          <FaSquareXTwitter className="text-black hover:opacity-70 transition text-[35px]" />
        </a>
      </div>
    </div>
  );
};

export default SocialLinks;
