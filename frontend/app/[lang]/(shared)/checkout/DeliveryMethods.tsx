"use client";

import React, { useEffect, useState } from "react";
import { CountryCode, DeliveryDto, DeliveryMethod } from "@/types";
import { useLoadData } from "@/hooks/useLoadData";
import { RadioGroup, ServiceUnavailable, Spinner } from "@/components";
import { useLangContext } from "@/context";
import { cn, formatPrice } from "@/utils";
import ChooseTerminal from "./ChooseTerminal";
import { useCheckoutContext } from "@/context/CheckoutContext";

type Props = {
  country: CountryCode;
};

const DeliveryMethods = ({ country }: Props) => {
  const { lang } = useLangContext();
  const { setSelectedDeliveryMethod } = useCheckoutContext();
  const {
    data: deliveryMethods,
    isLoading,
    error,
  } = useLoadData<DeliveryDto[]>(
    `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/delivery/per-country/${country}?lang=${lang}`,
    [country],
    [] as DeliveryDto[]
  );

  const [selectedMethod, setSelectedMethod] = useState<DeliveryMethod>(DeliveryMethod.PARCEL_MACHINE);
  const selectedDelivery = deliveryMethods?.find((dm) => dm.method === selectedMethod);

  useEffect(() => {
    selectedDelivery && setSelectedDeliveryMethod(selectedDelivery);
  }, [selectedDelivery]);

  useEffect(() => {
    setSelectedMethod(DeliveryMethod.PARCEL_MACHINE);
  }, [deliveryMethods]);

  if (isLoading) return <Spinner />;
  if (error || !deliveryMethods || deliveryMethods.length === 0) return <ServiceUnavailable />;
  return (
    <>
      <ul className="flex flex-col gap-6 mt-1 ml-2">
        {deliveryMethods?.map((dm) => {
          return (
            <li key={dm.id}>
              <div className="flex items-center justify-between">
                <RadioGroup
                  label={dm.title}
                  name="deliveryMethod"
                  value={dm.method}
                  selectedOption={selectedMethod}
                  setSelectedOption={(newlySelected: DeliveryMethod) => setSelectedMethod(newlySelected)}
                />
                <p className={cn(dm.method === selectedMethod && "font-bold")}>{formatPrice(dm.price)}</p>
              </div>
              <p className="text-sm italic mt-2">{dm.description}</p>
              {selectedMethod === DeliveryMethod.PARCEL_MACHINE && dm.method === DeliveryMethod.PARCEL_MACHINE && (
                <div className="mt-2">
                  <ChooseTerminal country={country} />
                </div>
              )}
            </li>
          );
        })}
      </ul>
    </>
  );
};

export default DeliveryMethods;
