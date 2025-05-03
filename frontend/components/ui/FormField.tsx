"use client";

import { cn } from "@/utils";
import React from "react";
import { useFormStatus } from "react-dom";

type Props = {
  label: string;
  name: string;
  type?: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  error?: string;
  required?: boolean;
  textarea?: boolean;
  className?: string;
  disabled?: boolean;
  defaultValue?: string;
};

const FormField: React.FC<Props> = ({
  label,
  name,
  type = "text",
  placeholder,
  value,
  onChange,
  error,
  required,
  textarea,
  className,
  disabled = false,
  defaultValue,
}) => {
  const { pending } = useFormStatus();
  return (
    <div className={cn("mb-4", className)}>
      <label htmlFor={name} className="block text-md font-medium text-gray-700 mb-1">
        {label} {required && <span className="text-red-500">*</span>}
      </label>

      {textarea ? (
        <textarea
          id={name}
          name={name}
          placeholder={placeholder}
          {...(value !== undefined ? { value, onChange } : { defaultValue })}
          className="w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright"
          disabled={disabled || pending}
        />
      ) : (
        <input
          id={name}
          name={name}
          type={type}
          placeholder={placeholder}
          {...(value !== undefined ? { value, onChange } : { defaultValue })}
          onChange={onChange}
          className={cn(
            "w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright",
            (disabled || pending) && "cursor-not-allowed bg-gray-400/30"
          )}
          disabled={disabled || pending}
        />
      )}

      {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
    </div>
  );
};

export default FormField;
