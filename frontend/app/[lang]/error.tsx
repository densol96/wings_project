"use client";
import React from "react";
import { useRouter } from "next/navigation";

import { Button } from "@/components/ui";

import { getLocale } from "@/utils";
import { useLangContext } from "@/context";

type Props = {
  error: Error;
  reset: () => void;
};

const errorMessages = {
  en: "We are experiencing some technical difficulties, and this service is temporarily unavailable. Please try again later. If the problem persists, contact site administration. Best regards!",
  lv: "Mums ir nelielas tehniskas problēmas, un šis pakalpojums pašlaik nav pieejams. Lūdzu, mēģiniet vēlreiz vēlāk. Ja problēma saglabājas, sazinieties ar vietnes administrāciju. Ar cieņu!",
} as const;

const subheading = {
  en: "Sparni Association",
  lv: "Sparni biedrība",
};

const btnText = {
  en: "Go home",
  lv: "Uz mājām",
};

const Error = ({ error, reset }: Props) => {
  const { lang } = useLangContext();
  const router = useRouter();

  return (
    <blockquote className="text-center font-bold w-[90%] lg:w-[50%] mx-auto absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
      <p className="text-xl lg:text-2xl">{errorMessages[lang]}</p>
      <p className="mt-6 text-primary-bright">{subheading[lang]}</p>
      <Button onClick={() => router.replace("/")} className="mt-10">
        {btnText[lang]}
      </Button>
    </blockquote>
  );
};

export default Error;
