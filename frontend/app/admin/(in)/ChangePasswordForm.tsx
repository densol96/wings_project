"use client";

import { changePassword } from "@/actions/auth/changePassword";
import { logout } from "@/actions/auth/logout";
import { Form, FormField, Heading } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { useFormSubmitReaction } from "@/hooks";
import { FormState } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  className?: string;
};

const ChangePasswordForm = ({ className }: Props) => {
  const [state, formAction] = useFormState<FormState, FormData>(changePassword, null);

  useFormSubmitReaction({
    state,
    onSuccess: () => {
      setTimeout(() => {
        toast.promise(
          new Promise((res) => {
            setTimeout(res, 2500);
          }),
          {
            loading: "Pāradresācija uz pieteikšanos..",
            success: "Lūdzu, piesakieties savam kontam vēlreiz",
          }
        );
      }, 0);
      setTimeout(logout, 2000);
    },
  });

  return (
    <div className={cn(className)}>
      <Heading size="xl">Mainīt paroli</Heading>
      <p className="mb-10">Pēc paroles maiņas jūs tiksiet izrakstīts un būs nepieciešams atkārtoti pierakstīties.</p>
      <Form className="md:gap-6 grid-cols-[1fr] gap-2" action={formAction}>
        <FormField label="Pašreizējā parole" name="oldPassword" type="password" error={normalizeError(state?.errors?.oldPassword)} required />
        <FormField label="Jaunā parole" name="password" type="password" error={normalizeError(state?.errors?.password)} required />
        <FormField label="Apstiprināt jauno paroli" name="confirmPassword" type="password" error={normalizeError(state?.errors?.confirmPassword)} required />
        <SubmitButton color="green">Mainīt e-pastu</SubmitButton>
      </Form>
    </div>
  );
};

export default ChangePasswordForm;
