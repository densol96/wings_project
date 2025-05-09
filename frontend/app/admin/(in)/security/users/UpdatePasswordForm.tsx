"use client";

import { updatePassword } from "@/actions/users/updatePassword";
import { Button, Form, FormField, Heading } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { FormState, IdParams } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  className?: string;
  id: number;
};

const UpdatePasswordForm = ({ className, id }: Props) => {
  const [state, formAction] = useFormState<FormState, FormData>(updatePassword, null);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
    }
  }, [state?.error, state?.success]);

  return (
    <Form action={formAction} cols={2} className={cn("md:gap-6 grid-cols-[1fr] gap-2 max-w-[800px] bg-gray-50", className)}>
      <Heading className="col-span-2" size="xl">
        {`Iestatīt jaunu paroli lietotājam ar ID #${id}`}
      </Heading>
      <input type="hidden" name="id" value={id} />
      <FormField label="Parole" name="password" type="password" error={normalizeError(state?.errors?.password)} required />
      <FormField label="Apstiprināt paroli" name="confirmPassword" type="password" error={normalizeError(state?.errors?.confirmPassword)} required />
      <div className="col-span-2 flex flex-col items-center gap-10">
        <SubmitButton className="w-[300px]" color="green">
          Apstiprināt
        </SubmitButton>
        <Button onClick={() => router.back()}>Atgriezties</Button>
      </div>
    </Form>
  );
};

export default UpdatePasswordForm;
