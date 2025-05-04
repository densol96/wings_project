"use client";

import { FormField } from "@/components";
import { TbPasswordUser } from "react-icons/tb";
import AuthForm from "../AuthForm";
import { useFormState } from "react-dom";
import { login } from "@/actions/auth/login";
import { FormState } from "@/types";
import { useEffect } from "react";
import toast from "react-hot-toast";
import { normalizeError } from "@/utils";

const Page = () => {
  const [state, formAction] = useFormState<FormState, FormData>(login, null);

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    }
  }, [state?.error]);

  return (
    <AuthForm
      title="Ielogoties sistēmā"
      action={formAction}
      link={{
        label: "Atjaunot paroli",
        href: "/admin/forgot-password",
        icon: <TbPasswordUser />,
      }}
    >
      <FormField label="Lietotājvārds" name="username" error={normalizeError(state?.errors?.username)} required />
      <FormField label="Parole" name="password" type="password" error={normalizeError(state?.errors?.password)} required />
    </AuthForm>
  );
};

export default Page;
