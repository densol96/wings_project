"use client";

import React, { useEffect, useState } from "react";
import { Button, Spinner } from "../ui";
import { FooterSubscribeSection } from "@/types/sections/footer";
import { Locale } from "@/types";
import toast from "react-hot-toast";
import { useLangContext } from "@/context/LangContext";
import { useRequestAction } from "@/hooks";

type Props = {
  subscribeSection: FooterSubscribeSection;
};

const SubscribeForm = ({ subscribeSection }: Props) => {
  const [email, setEmail] = useState("");

  const {
    response,
    isLoading,
    error,
    request: makeRequest,
  } = useRequestAction<{ message: string }>({
    endpoint: "newsletter/subscribe?",
    body: {
      email,
    },
    onSuccess: (result: { message: string }) => toast.success(result.message),
    onError: (e: Error) => toast.error(e.message),
  });

  return (
    <form
      className="flex flex-col"
      onSubmit={(e) => {
        e.preventDefault();
        makeRequest();
      }}
    >
      <h2 className="mb-3 text-sm font-bold tracking-widest text-gray-800 uppercase">{subscribeSection.title}</h2>
      <input value={email} onChange={(e) => setEmail(e.target.value)} className="custom-input" placeholder={subscribeSection.email} type="text" />
      <Button disabled={isLoading} className="mt-2">
        {isLoading ? <Spinner color="white" /> : subscribeSection.subscribe}
      </Button>
    </form>
  );
};

export default SubscribeForm;
