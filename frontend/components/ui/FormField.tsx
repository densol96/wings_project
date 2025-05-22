"use client";

import { cn } from "@/utils";
import React from "react";
import { useFormStatus } from "react-dom";

type Props<T extends string | number> = {
  label: string;
  name: string;
  type?: string;
  placeholder?: string;
  value?: T;
  onChange?: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  error?: string;
  required?: boolean;
  textarea?: boolean;
  className?: string;
  disabled?: boolean;
  defaultValue?: T;
  inputClassName?: string;
  checked?: boolean;
  defaultChecked?: boolean;
  multiple?: boolean;
  accept?: string;
};

const FormField = <T extends string | number>({
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
  inputClassName,
  checked,
  defaultChecked,
  multiple,
  accept,
}: Props<T>) => {
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
          {...(value !== undefined ? { value: value as string, onChange } : { defaultValue })}
          className={cn("w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright", inputClassName)}
          disabled={disabled || pending}
        />
      ) : type === "checkbox" ? (
        <input
          id={name}
          name={name}
          type="checkbox"
          {...(checked !== undefined ? { checked, onChange } : { defaultChecked })}
          onChange={onChange}
          className={cn("accent-primary-bright", (disabled || pending) && "cursor-not-allowed bg-gray-400/30", inputClassName)}
          disabled={disabled || pending}
        />
      ) : (
        <input
          id={name}
          name={name}
          type={type}
          placeholder={placeholder}
          {...(value !== undefined ? { value: value as string | number, onChange } : { defaultValue })}
          className={cn(
            "p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-primary-bright accent-primary-bright",
            (disabled || pending) && "cursor-not-allowed bg-gray-400/30",
            "w-full shadow-md",
            inputClassName
          )}
          disabled={disabled || pending}
          multiple={multiple}
        />
      )}

      {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
    </div>
  );
};

export default FormField;
