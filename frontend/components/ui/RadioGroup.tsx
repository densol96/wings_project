import { cn } from "@/utils";
import React from "react";

type Props<T extends string> = {
  className?: string;
  checkboxClassName?: string;
  name: string;
  value: T;
  selectedOption: T;
  setSelectedOption: (newValue: T) => void;
  label: string;
};

const RadioGroup = <T extends string>({ className, checkboxClassName, name, value, selectedOption, setSelectedOption, label }: Props<T>) => {
  return (
    <div className={cn("flex flex-row gap-2 items-center", className)}>
      <input
        className={cn("accent-primary-bright", checkboxClassName)}
        type="radio"
        id={name + value}
        name={name}
        value={value}
        checked={selectedOption === value}
        onChange={(e) => setSelectedOption(e.target.value as T)}
      />
      <label htmlFor={name + value}>{label}</label>
    </div>
  );
};

export default RadioGroup;
