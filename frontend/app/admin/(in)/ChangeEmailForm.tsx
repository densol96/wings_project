"use client";

import { Heading, Input } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import useClientFormSubmit from "@/hooks/useClientFormSubmit";
import { cn } from "@/utils";
import { useRouter } from "next/navigation";
import { useState } from "react";
import toast from "react-hot-toast";

type Props = {
  currentEmail: string;
  className?: string;
};

const ChangeEmailForm = ({ currentEmail, className }: Props) => {
  const [email, setEmail] = useState(currentEmail);
  const router = useRouter();

  const { formFieldErrors, isSubmitting, submit } = useClientFormSubmit(
    `${process.env.NEXT_PUBLIC_FRONTEND_URL}/api/change-email`,
    {
      body: { email },
      method: "PATCH",
    },
    {
      onSuccess: () => {
        setTimeout(() => {
          router.back();
        }, 1000);
      },
    }
  );

  return (
    <div className={cn(className)}>
      <Heading size="xl">Mainīt e-pastu</Heading>
      <p className="mb-10">Pēc e-pasta adreses maiņas jūs tiksiet izrakstīts un būs nepieciešams atkārtoti pierakstīties.</p>
      <form className="flex flex-col" onSubmit={submit}>
        <div className="flex flex-row gap-4">
          <Input className={cn("flex-1")} name="email" type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
          <SubmitButton
            isPending={isSubmitting}
            color="green"
            className={cn(email !== currentEmail && email ? "opacity-100" : "opacity-0", "w-auto transition-none")}
          >
            Mainīt e-pastu
          </SubmitButton>
        </div>
        {formFieldErrors?.email && <p className="text-red-700 mt-4">{formFieldErrors?.email}</p>}
      </form>
    </div>
  );
};

export default ChangeEmailForm;
