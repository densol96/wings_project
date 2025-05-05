import { cn } from "@/utils";

type Props = {
  name: string;
  type?: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  disabled?: boolean;
  defaultValue?: string;
  pending?: boolean;
  className?: string;
};

const Input = ({ name, type = "text", placeholder, value, defaultValue, onChange, disabled = false, pending = false, className }: Props) => {
  return (
    <input
      id={name}
      name={name}
      type={type}
      placeholder={placeholder}
      {...(value !== undefined ? { value, onChange } : { defaultValue })}
      onChange={onChange}
      className={cn(
        "w-full p-2 border rounded-md shadow-md focus:outline-none focus:ring-2 focus:ring-primary-bright",
        className,
        (disabled || pending) && "cursor-not-allowed bg-gray-400/30"
      )}
      disabled={disabled || pending}
    />
  );
};

export default Input;
