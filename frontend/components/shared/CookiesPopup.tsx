"use client";
// This component is using WEB APIs such asd localStorage.
// Make sure u dynamically import it to a server component (likely top-level layout.tsx) and opt-out of SSR!

import React from "react";
import { Locale } from "@/types";
import { Button } from "@/components/ui";
import Link from "next/link";
import { useLocalStorage } from "@/hooks";

export type CookiesPopup = {
  text: string;
  moreBtn: string;
  close: string;
  href: string;
};

type Props = {
  dict: CookiesPopup;
  lang: Locale;
};

const LOCAL_STORAGE_KEY = "cookies_agreed_to_by_user";
// const sevenDaysInMs = 7 * 24 * 60 * 60 * 1000;
const ACCEPT_ANSWER_FOR = 1000 * 60; // 1 min

const CookiesPopup = ({ dict }: Props) => {
  // "2025-03-10T22:00:00"
  const { value, updateLocalStorage, deleteFromLocalStorage } = useLocalStorage<string>(LOCAL_STORAGE_KEY);

  const needsFreshConsent = () => {
    if (value === null) return true;

    const storedTime = new Date(value);
    if (isNaN(storedTime.getTime())) return true;

    const timePassed = new Date().getTime() - storedTime.getTime();
    return timePassed >= ACCEPT_ANSWER_FOR;
  };

  if (!needsFreshConsent()) return null;

  return (
    <div className="fixed left-0 bottom-0 w-full flex px-12 py-6 bg-gray-50 opacity-90 shadow-custom-med items-center gap-10 z-20">
      <p className="flex-1 text-center">{dict.text}</p>
      <div className="flex md:flex-row flex-col gap-4">
        <Link href={dict.href}>
          <Button>{dict.moreBtn}</Button>
        </Link>
        <Button onClick={() => updateLocalStorage(new Date().toISOString())}>{dict.close}</Button>
      </div>
    </div>
  );
};

export default CookiesPopup;
