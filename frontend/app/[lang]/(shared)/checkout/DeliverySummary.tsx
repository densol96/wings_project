"use client";

import { Button, Heading, SimpleSelect } from "@/components";
import { useCartContext, useLangContext } from "@/context";
import { Country, CountryCode, DeliveryMethod } from "@/types";
import { formatPrice, pickLabels } from "@/utils";
import React, { useState } from "react";
import DeliveryMethods from "./DeliveryMethods";
import { useCheckoutContext } from "@/context/CheckoutContext";
import { useRouter } from "next/navigation";
import ApplyCoupon from "./ApplyCoupon";

type Translations = {
  cartTotals: string;
  total: string;
  delivery: string;
  country: string;
  countryList: Country[];
  totalTogether: string;
  proceedWithOrder: string;
  useCoupon: string;
  applyCoupon: string;
  discount: string;
  enterDescription: string;
  explanation: string;
};

type Props = {
  translations: Translations;
};

const DeliverySummary = ({ translations }: Props) => {
  const { getTotalPrice, cartIsLoaded, items } = useCartContext();
  const { selectedDeliveryMethod, selectedTerminal, selectedCountry, setSelectedCountry, coupon } = useCheckoutContext();
  const { lang } = useLangContext();
  const router = useRouter();

  let readyToProceed =
    !!selectedDeliveryMethod && (selectedDeliveryMethod.method === DeliveryMethod.PARCEL_MACHINE ? selectedTerminal?.country === selectedCountry : true);

  if (!cartIsLoaded || items.length === 0) return null;
  return (
    <div className="">
      <Heading as="h2" size="sm" className="uppercase tracking-widest font-bold text-gray-700">
        {translations.cartTotals}
      </Heading>
      <div className="flex items-center justify-between mt-6 mb-4">
        <p>{translations.total}</p>
        <p className="font-bold">{formatPrice(getTotalPrice())}</p>
      </div>
      <div className="flex items-center justify-between mt-4">
        <label htmlFor="countryCode">{translations.country}</label>
        <SimpleSelect
          defaultValue="LV"
          value={selectedCountry}
          onChange={setSelectedCountry}
          name="countryCode"
          id="countryCode"
          options={translations.countryList}
          render={(country: Country) => <option value={country.code}>{country.name}</option>}
        />
      </div>
      <p className="mt-4">{translations.delivery}</p>
      <DeliveryMethods country={selectedCountry} />
      <ApplyCoupon translations={pickLabels(translations, ["applyCoupon", "useCoupon", "discount", "enterDescription", "explanation"])} />
      <div className="mt-4 border-t-1 border-gray-300 flex justify-between items-center pt-2">
        <p className="uppercase font-medium">{translations.totalTogether}</p>
        <p className="font-bold">{formatPrice(getTotalPrice() + (selectedDeliveryMethod?.price || 0) - (coupon?.discount || 0))}</p>
      </div>
      {readyToProceed && (
        <div>
          <Button color="green" onClick={() => router.push(`/${lang}/checkout/form`)} className="w-full mt-4">
            {translations.proceedWithOrder}
          </Button>
        </div>
      )}
    </div>
  );
};

export default DeliverySummary;
