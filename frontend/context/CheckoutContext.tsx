"use client";

import { CountryCode, Coupon, DeliveryDto, DeliveryMethod, TerminalDto } from "@/types";
import { createContext, ReactNode, useContext, useState } from "react";

export type CheckoutState = {
  selectedDeliveryMethod?: DeliveryDto;
  selectedTerminal?: TerminalDto;
  selectedCountry: CountryCode;
  coupon?: Coupon;
  setSelectedDeliveryMethod: (dm: DeliveryDto) => void;
  setSelectedTerminal: (t: TerminalDto) => void;
  setSelectedCountry: (c: CountryCode) => void;
  setCoupon: (c?: Coupon) => void;
};

const CheckoutContext = createContext<CheckoutState | undefined>(undefined);

export const CheckoutProvider = ({ children }: { children: React.ReactNode }) => {
  const [selectedDeliveryMethod, setSelectedDeliveryMethod] = useState<DeliveryDto>();
  const [selectedTerminal, setSelectedTerminal] = useState<TerminalDto>();
  const [selectedCountry, setSelectedCountry] = useState<CountryCode>("LV");
  const [coupon, setCoupon] = useState<Coupon>();
  //   const [contactInfo, setContactInfo] = useState<ContactFormData>();

  return (
    <CheckoutContext.Provider
      value={{
        selectedDeliveryMethod,
        selectedTerminal,
        selectedCountry,
        setSelectedDeliveryMethod,
        setSelectedTerminal,
        setSelectedCountry,
        coupon,
        setCoupon,
      }}
    >
      {children}
    </CheckoutContext.Provider>
  );
};

export const useCheckoutContext = () => {
  const context = useContext(CheckoutContext);
  if (!context) throw new Error("useCheckout must be used inside CheckoutProvider");
  return context;
};
