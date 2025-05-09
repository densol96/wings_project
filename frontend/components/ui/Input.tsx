import { cn } from "@/utils";

type Props = {
  name: string;
  type?: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => void;
  disabled?: boolean;
  defaultValue?: string;
  pending?: boolean;
  className?: string;
  checked?: boolean;
  defaultChecked?: boolean;
  id?: string;
};

const Input = ({
  name,
  type = "text",
  placeholder,
  value,
  defaultValue,
  onChange,
  disabled = false,
  pending = false,
  className,
  checked,
  defaultChecked,
  id,
}: Props) => {
  return type === "textarea" ? (
    <textarea
      id={id || name}
      name={name}
      placeholder={placeholder}
      {...(value !== undefined ? { value: value as string, onChange } : { defaultValue })}
      className={cn("w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright", className)}
      disabled={disabled || pending}
    />
  ) : type === "checkbox" ? (
    <input
      id={id || name}
      name={name}
      type="checkbox"
      {...(checked !== undefined ? { checked, onChange } : { defaultChecked })}
      {...(value !== undefined ? { value: value as string | number, onChange } : { defaultValue })}
      onChange={onChange}
      className={cn("accent-primary-bright", (disabled || pending) && "cursor-not-allowed bg-gray-400/30", className)}
      disabled={disabled || pending}
    />
  ) : (
    <input
      id={id || name}
      name={name}
      type={type}
      placeholder={placeholder}
      {...(value !== undefined ? { value: value as string | number, onChange } : { defaultValue })}
      className={cn(
        "p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-primary-bright accent-primary-bright",
        (disabled || pending) && "cursor-not-allowed bg-gray-400/30",
        "w-full shadow-md",
        className
      )}
      disabled={disabled || pending}
    />
  );
};

export default Input;
