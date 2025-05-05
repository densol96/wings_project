import { HttpMethod } from "@/types";
import { handleFormSubmission } from "@/utils";
import { FormEvent, useState } from "react";

const useClientFormSubmit = (
  endpoint: string,
  {
    body,
    method = "POST",
    headers,
    additionalOptions,
  }: {
    body?: Record<string, any>;
    method?: HttpMethod;
    headers?: HeadersInit;
    additionalOptions?: RequestInit;
  } = {},
  {
    onSuccess,
    onError,
    onGoneError,
    onNetworkError,
  }: {
    onSuccess?: (data: { message: string }) => void;
    onError?: (response: Response, error: { message: string }) => void;
    onGoneError?: (error: { message: string }) => void;
    onNetworkError?: (error: any) => void;
  } = {}
) => {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [formFieldErrors, setFormFieldErrors] = useState<Record<string, string>>({});

  const submit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsSubmitting(true);
    setFormFieldErrors({});
    const fieldErrors = await handleFormSubmission(
      endpoint,
      {
        body,
        method,
        headers,
        additionalOptions,
      },
      onSuccess,
      onError,
      onGoneError,
      onNetworkError
    );
    setIsSubmitting(false);
    if (fieldErrors) setFormFieldErrors(fieldErrors);
  };

  return { formFieldErrors, isSubmitting, submit };
};

export default useClientFormSubmit;
