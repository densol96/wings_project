"use client";

import { CheckoutErrors, CountryCode, Coupon, DeliveryDto, DeliveryMethod, ErrorData, TerminalDto } from "@/types";
import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { useCartContext } from "./CartContext";
import { useRequestAction } from "@/hooks";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import { useLangContext } from "./LangContext";

const formInitState = {
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
  address: {
    street: "",
    houseNumber: "",
    apartment: "",
    city: "",
    postalCode: "",
  },
  additionalDetails: "",
};
type CheckoutFormData = typeof formInitState;

export type CheckoutState = {
  selectedDeliveryMethod?: DeliveryDto;
  selectedTerminal?: TerminalDto;
  selectedCountry: CountryCode;
  coupon?: Coupon;
  form: CheckoutFormData;
  paymentIndentSecret?: string;
  handleFormChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => void;
  checkoutErrors?: CheckoutErrors;
  setCheckoutErrors: (errrorReponse: CheckoutErrors) => void;
  setSelectedDeliveryMethod: (dm: DeliveryDto) => void;
  setSelectedTerminal: (t: TerminalDto) => void;
  setSelectedCountry: (c: CountryCode) => void;
  setCoupon: (c?: Coupon) => void;
  createIndent: () => void;
  isLoading: boolean;
};

const CheckoutContext = createContext<CheckoutState | undefined>(undefined);

export const CheckoutProvider = ({ children }: { children: React.ReactNode }) => {
  const [selectedDeliveryMethod, setSelectedDeliveryMethod] = useState<DeliveryDto>();
  const [selectedTerminal, setSelectedTerminal] = useState<TerminalDto>();
  const [selectedCountry, setSelectedCountry] = useState<CountryCode>("LV");
  const [coupon, setCoupon] = useState<Coupon>();
  const [form, setForm] = useState<CheckoutFormData>(formInitState);
  const [checkoutErrors, setCheckoutErrors] = useState<CheckoutErrors>();
  const [paymentIndentSecret, setPaymentIndentSecret] = useState<string>();

  const { items, getTotalPrice } = useCartContext();

  const router = useRouter();
  const { lang } = useLangContext();

  const handleFormChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;

    if (name.startsWith("address.")) {
      const key = name.replace("address.", "");
      setForm((prev) => ({
        ...prev,
        address: {
          ...prev.address,
          [key]: value,
        },
      }));
    } else {
      setForm((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const { isLoading, request: createIndent } = useRequestAction<{ clientSecret: string }, CheckoutErrors>({
    endpoint: "payments/create-payment-intent",
    body: {
      orderItems: items.map((item) => ({ id: item.id, amount: item.quantity })),
      deliveryMethodVariationId: selectedDeliveryMethod?.id,
      terminalId: selectedTerminal?.id,
      customerInfo: { ...form, address: selectedDeliveryMethod?.method === DeliveryMethod.COURIER ? { ...form.address, country: selectedCountry } : null },
      couponCode: coupon?.code,
      total: getTotalPrice() + (selectedDeliveryMethod?.price || 0) - (coupon?.discount || 0),
    },
    onSuccess: (paymentIndent: { clientSecret: string }) => {
      setPaymentIndentSecret(paymentIndent.clientSecret);
      router.push(`/${lang}/checkout/payment`);
    },
    onError: (e: CheckoutErrors) => {
      console.log("EEROR", e);
      toast.error(e.message);
      setCheckoutErrors(e);
    },
    onFallbackError: (e: Error) => toast.error(e.message),
  });

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
        form,
        handleFormChange,
        checkoutErrors,
        setCheckoutErrors,
        paymentIndentSecret,
        createIndent,
        isLoading,
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
