"use client";

import { Form, FormField } from "@/components";
import { useCheckoutContext } from "@/context/CheckoutContext";
import { DeliveryMethod, PersonalInfoFormDictionary } from "@/types";
import React, { useState } from "react";

type Props = {
  dict: PersonalInfoFormDictionary;
};

const placeholders = {
  LV: {
    phone: "+371...",
    postCode: "LV-...",
  },
  LT: {
    phone: "+370...",
    postCode: "LT-...",
  },
  EE: {
    phone: "+372...",
    postCode: "EE-...",
  },
};

const PersonalInfoForm = ({ dict }: Props) => {
  const { selectedCountry, form, handleFormChange: handleChange, checkoutErrors, selectedDeliveryMethod } = useCheckoutContext();
  const errors = checkoutErrors?.fieldErrors;

  const requiresAddress = selectedDeliveryMethod?.method === DeliveryMethod.COURIER;

  return (
    <Form cols={2} className="md:gap-6 grid-cols-[1fr] gap-0">
      <FormField label={dict.firstName} name="firstName" value={form.firstName} error={errors?.["firstName"]} onChange={handleChange} required />
      <FormField label={dict.lastName} name="lastName" value={form.lastName} error={errors?.["lastName"]} onChange={handleChange} required />
      <FormField label={dict.email} name="email" type="email" value={form.email} error={errors?.["email"]} onChange={handleChange} required />
      <FormField
        label={dict.phoneNumber}
        name="phoneNumber"
        value={form.phoneNumber}
        error={errors?.["phoneNumber"]}
        onChange={handleChange}
        placeholder={placeholders[selectedCountry].phone}
        required
      />
      {requiresAddress && (
        <>
          <FormField
            label={dict.street}
            name="address.street"
            value={form.address.street}
            error={errors?.["address.street"]}
            onChange={handleChange}
            required
          />
          <FormField
            label={dict.houseNumber}
            name="address.houseNumber"
            value={form.address.houseNumber}
            error={errors?.["address.houseNumber"]}
            onChange={handleChange}
            required
          />
          <FormField
            label={dict.apartment}
            name="address.apartment"
            value={form.address.apartment}
            error={errors?.["address.apartment"]}
            onChange={handleChange}
          />
          <FormField label={dict.city} name="address.city" value={form.address.city} error={errors?.["address.city"]} onChange={handleChange} required />
          <FormField
            label={dict.postalCode}
            name="address.postalCode"
            value={form.address.postalCode}
            error={errors?.["address.postalCode"]}
            onChange={handleChange}
            placeholder={placeholders[selectedCountry].postCode}
            required
          />
          <FormField label={dict.country} name="country" value={selectedCountry} required disabled />{" "}
        </>
      )}

      <FormField
        type="textarea"
        label={dict.additionalDetails}
        name="additionalDetails"
        value={form.additionalDetails}
        onChange={handleChange}
        error={errors?.additionalDetails}
        textarea={true}
        className="md:col-span-2 col-span-1"
      />
    </Form>
  );
};

export default PersonalInfoForm;
