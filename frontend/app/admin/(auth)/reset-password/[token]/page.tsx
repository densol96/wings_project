"use client";

import { TokenProps } from "@/types";
import { FormField, StyledLink } from "@/components";
import { displayError, normalizeError } from "@/utils";
import { useEffect, useState } from "react";
import AuthForm from "../../AuthForm";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import useClientFormSubmit from "@/hooks/useClientFormSubmit";
import { IoLogInSharp } from "react-icons/io5";

const Page = ({ params: { token } }: TokenProps) => {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const router = useRouter();
  const { formFieldErrors, isSubmitting, submit } = useClientFormSubmit(
    `auth/reset-password/${token}`,
    {
      body: { password, confirmPassword },
    },
    {
      onSuccess: () => {
        setTimeout(() => {
          router.replace("/admin/login");
        }, 3000);
      },
      onGoneError: () => {
        setTimeout(() => {
          router.replace("/admin/forgot-password");
        }, 3000);
      },
    }
  );

  return (
    <div>
      <>
        <AuthForm
          title="Paroles maiņa"
          onSubmit={submit}
          isPending={isSubmitting}
          link={{
            label: "Atgriezties uz pieteikšanos",
            href: "/admin/login",
            icon: <IoLogInSharp />,
          }}
        >
          <FormField
            label="Jauna parole"
            type="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            error={normalizeError(formFieldErrors?.password)}
            disabled={isSubmitting}
            required
          />
          <FormField
            label="Paroles apstiprinājums"
            type="password"
            name="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            error={normalizeError(formFieldErrors?.confirmPassword)}
            disabled={isSubmitting}
            required
          />
        </AuthForm>
      </>
    </div>
  );
};

export default Page;
