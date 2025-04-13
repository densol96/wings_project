"use client";

import { ServiceUnavailable, Spinner } from "@/components";
import { useLangContext } from "@/context";
import { useCheckoutContext } from "@/context/CheckoutContext";
import { useParcelLockers } from "@/hooks";
import { CountryCode, DeliveryMethod, TerminalDto } from "@/types";
import React, { useEffect, useRef } from "react";
import Select, { SelectInstance } from "react-select";

type Props = {
  country: CountryCode;
};

const dict = {
  en: "Select a terminal...",
  lv: "Izvēlēties pakomātu...",
};

type OptionType = {
  label: string;
  value: number;
};

const ChooseTerminal = ({ country }: Props) => {
  const { lang } = useLangContext();
  const { setSelectedTerminal, selectedTerminal, selectedDeliveryMethod } = useCheckoutContext();
  const { data, isLoading, isError } = useParcelLockers<TerminalDto[]>(country, lang);

  const ref = useRef<SelectInstance>(null);

  const options: OptionType[] | undefined = data?.map((terminal) => ({
    label: `${terminal.name} - ${terminal.address}`,
    value: terminal.id,
  }));

  useEffect(() => {
    if (selectedDeliveryMethod?.method === DeliveryMethod.PARCEL_MACHINE && !selectedTerminal) {
      ref.current?.focus();
    }
  }, [selectedDeliveryMethod]);

  useEffect(() => {
    !selectedTerminal && ref.current?.focus();
  }, [data]);

  if (isLoading) return <Spinner />;
  return isError ? (
    <ServiceUnavailable />
  ) : (
    <Select
      ref={ref}
      options={options}
      value={
        selectedTerminal
          ? {
              label: `${selectedTerminal.name} - ${selectedTerminal.address}`,
              value: selectedTerminal.id,
            }
          : null
      }
      onChange={(selectedOption) => {
        const found = data?.find((t) => t.id === selectedOption?.value);
        if (found) setSelectedTerminal(found);
      }}
      placeholder={dict[lang]}
    />
  );
};

export default ChooseTerminal;
