"use client";

import { FormField } from "@/components";
import AuthForm from "../AuthForm";
import { handleFormSubmission, normalizeError } from "@/utils";
import { useState } from "react";
import useClientFormSubmit from "@/hooks/useClientFormSubmit";

const Page = () => {
  const [username, setUsername] = useState("");
  const { formFieldErrors, isSubmitting, submit } = useClientFormSubmit("auth/request-reset-password", {
    body: { username },
  });
  return (
    <AuthForm title="Pieprasīt paroles atiestatīšanu" onSubmit={submit} isPending={isSubmitting}>
      <FormField
        label="Lietotājvārds"
        name="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        error={formFieldErrors?.username}
        disabled={isSubmitting}
        required
      />
    </AuthForm>
  );
};

export default Page;
