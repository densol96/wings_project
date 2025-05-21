"use client";

import deleteRole from "@/actions/roles/deleteRole";
import SubmitButton from "@/components/ui/SubmitButton";
import { FormState } from "@/types";
import { useEffect } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";
import { MdDelete } from "react-icons/md";

type Props = {
  id: number;
  action: (prevState: FormState, formData: FormData) => Promise<FormState>;
};

const DeleteBtn = ({ id, action }: Props) => {
  const [state, formAction] = useFormState<FormState, FormData>(action, null);
  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
    }
  }, [state?.error, state?.success]);

  return (
    <form action={formAction}>
      <input key={id} type="hidden" name="id" value={id} />
      <SubmitButton className="p-2">
        <MdDelete size={20} />
      </SubmitButton>
    </form>
  );
};

export default DeleteBtn;
