"use client";

import { defaultLocale, locales } from "@/constants/locales";
import { Locale, LocaleAndString } from "@/types";
import { cn } from "@/utils";
import { useFormStatus } from "react-dom";

type LocalisedValues = {
  locale: Locale;
  value: string;
}[];

type Props = {
  label: string;
  name: string;
  placeholder: {
    [key in Locale]: string;
  };
  error?: LocaleAndString;
  required?: boolean;
  textarea?: boolean;
  className?: string;
  disabled?: boolean;
  defaultValue?: {
    [key in Locale]: string;
  };
  inputClassName?: string;
  requiresTranslationsFields?: boolean;
  rows?: number;
  onChange?: (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => void;
  localisedValues?: LocalisedValues;
};

const MultiLangFormField = ({
  label,
  name,
  error,
  required,
  textarea = false,
  className,
  disabled = false,
  inputClassName,
  requiresTranslationsFields = false,
  placeholder,
  rows,
  onChange,
  localisedValues,
}: Props) => {
  const { pending } = useFormStatus();

  return (
    <div className={cn("mb-4", className)}>
      <label htmlFor={name} className="block text-md font-medium text-gray-700 mb-1">
        {label} {required && <span className="text-red-500">*</span>}
      </label>
      <SingleInput
        name={name}
        textarea={textarea}
        locale={defaultLocale}
        placeholder={placeholder?.[defaultLocale]}
        disabled={disabled || pending}
        error={error?.[defaultLocale]}
        rows={rows}
        requiresTranslationsFields={requiresTranslationsFields}
        onChange={onChange}
        value={localisedValues?.find((t) => t.locale === defaultLocale)?.value}
      />
      {requiresTranslationsFields &&
        locales
          .map((l) => l.locale) // ["lv", "en"]
          .filter((locale) => locale !== defaultLocale)
          .map((requiredAdditionalLocale) => (
            <SingleInput
              name={name}
              textarea={textarea}
              locale={requiredAdditionalLocale}
              placeholder={placeholder?.[requiredAdditionalLocale]}
              disabled={disabled}
              error={error?.[requiredAdditionalLocale]}
              inputClassName={inputClassName}
              requiresTranslationsFields={requiresTranslationsFields}
              onChange={onChange}
              value={localisedValues?.find((t) => t.locale === requiredAdditionalLocale)?.value}
            />
          ))}
    </div>
  );
};

export default MultiLangFormField;

type SingleProps = {
  name: string;
  textarea: boolean;
  locale: Locale;
  placeholder: string;
  disabled: boolean;
  error?: string;
  defaultValue?: string;
  inputClassName?: string;
  rows?: number;
  requiresTranslationsFields: boolean;
  onChange?: (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => void;
  value?: string;
};

export const SingleInput = ({
  textarea,
  locale,
  name,
  defaultValue,
  disabled,
  inputClassName,
  error,
  placeholder,
  rows = 5,
  requiresTranslationsFields,
  onChange,
  value,
}: SingleProps) => {
  const uniqueName = name + "-" + locale;
  return (
    <div>
      <div className="flex gap-2">
        {requiresTranslationsFields && <p className="uppercase font-bold text-gray-500 mt-1">{locale}:</p>}
        {textarea ? (
          <textarea
            id={uniqueName}
            name={uniqueName}
            defaultValue={defaultValue}
            className={cn("w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright mb-2", inputClassName)}
            disabled={disabled}
            placeholder={placeholder}
            rows={rows}
            onChange={onChange}
            {...(value && { value })}
          />
        ) : (
          <input
            id={uniqueName}
            name={uniqueName}
            type="text"
            defaultValue={defaultValue}
            className={cn(
              "p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-primary-bright accent-primary-bright mb-2",
              disabled && "cursor-not-allowed bg-gray-400/30",
              "w-full shadow-md",
              inputClassName
            )}
            disabled={disabled}
            placeholder={placeholder}
            onChange={onChange}
            {...(value && { value })}
          />
        )}
      </div>
      {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
    </div>
  );
};
