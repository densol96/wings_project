import { FormState } from "@/types";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

type Props = {
  state: FormState;
  onSuccess?: () => void;
  onFailure?: () => void;
};

const useFormSubmitReaction = ({ state, onSuccess, onFailure }: Props) => {
  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
      onFailure?.();
    } else if (state?.success?.message) {
      toast.success(state?.success?.message);
      onSuccess?.();
    }
  }, [state?.error, state?.success]);
};

export default useFormSubmitReaction;
