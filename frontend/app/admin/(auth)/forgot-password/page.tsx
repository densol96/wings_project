"use client";

import { FormField, StyledLink } from "@/components";
import AuthForm from "../AuthForm";
import { handleFormSubmission, normalizeError } from "@/utils";
import { useState } from "react";
import useClientFormSubmit from "@/hooks/useClientFormSubmit";
import { IoLogInSharp } from "react-icons/io5";

const Page = () => {
  const [username, setUsername] = useState("");
  const { formFieldErrors, isSubmitting, submit } = useClientFormSubmit("auth/request-reset-password", {
    body: { username },
  });
  return (
    <div>
      <AuthForm
        title="Pieprasīt paroles atiestatīšanu"
        onSubmit={submit}
        isPending={isSubmitting}
        link={{
          label: "Atgriezties uz pieteikšanos",
          href: "/admin/login",
          icon: <IoLogInSharp />,
        }}
      >
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
    </div>
  );
};

export default Page;
