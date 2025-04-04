"use client";

import React, { useState } from "react";
import { Button, Spinner } from "../ui";
import { FooterSubscribeSection } from "@/types/sections/footer";
import { Locale } from "@/types";
import toast from "react-hot-toast";

type Props = {
  subscribeSection: FooterSubscribeSection;
  lang: Locale;
};

const SubscribeForm = ({ subscribeSection, lang }: Props) => {
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const subscribeForNewsNotifications = async () => {
    try {
      setIsLoading(true);
      const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/newsletter/subscribe?lang=${lang}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
        }),
      });
      const data = await response.json();

      /*
       * Good response returns {message}
       * Bas response either {fieldName: problem} (for input validation, in this case -> email) or {message} (BasicErrorDto)
       */

      if (response.ok) toast.success(data.message);
      else {
        console.error("Bad response from news subscription service (check footer component):", data);
        toast.error(Object.values(data)[0] as string);
      }
    } catch (e: any) {
      console.error(e);
      toast.success(subscribeSection.generalError);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col">
      <h2 className="mb-3 text-sm font-bold tracking-widest text-gray-800 uppercase">{subscribeSection.title}</h2>
      <input value={email} onChange={(e) => setEmail(e.target.value)} className="custom-input" placeholder={subscribeSection.email} type="text" />
      <Button disabled={isLoading} onClick={subscribeForNewsNotifications} className="mt-2">
        {isLoading ? <Spinner color="white" /> : subscribeSection.subscribe}
      </Button>
    </div>
  );
};

export default SubscribeForm;
