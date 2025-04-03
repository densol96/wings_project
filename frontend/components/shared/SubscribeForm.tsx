"use client";

import React from "react";
import { Button, Input } from "../ui";
import { FooterSubscribeSection } from "@/types/sections/footer";

type Props = {
  subscribeSection: FooterSubscribeSection;
};

const SubscribeForm = ({ subscribeSection }: Props) => {
  return (
    <div className="flex flex-col">
      <h2 className="mb-3 text-sm font-bold tracking-widest text-gray-800 uppercase">{subscribeSection.title}</h2>
      <Input placeholder={subscribeSection.email} />
      <Button onClick={() => alert("In progress...")} className="mt-2">
        {subscribeSection.subscribe}
      </Button>
    </div>
  );
};

export default SubscribeForm;
