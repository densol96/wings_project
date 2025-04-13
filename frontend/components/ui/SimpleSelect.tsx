import { cn } from "@/utils";
import React from "react";

type Props<T extends string | number, V> = {
  defaultValue: T;
  value: T;
  onChange: (newValue: T) => void;
  options: V[];
  name?: string;
  id?: string;
  className?: string;
  render: (option: V) => React.ReactNode;
};

const SimpleSelect = <T extends string | number, V>({
  defaultValue,
  value,
  onChange,
  options,
  name = "select",
  id = "select",
  className,
  render,
}: Props<T, V>) => {
  return (
    <select
      defaultValue={defaultValue}
      value={value}
      onChange={(e) => onChange(e.target.value as T)}
      name={name}
      id={id}
      className={cn("border-1 border-gray-300 p-1", className)}
    >
      {options.map(render)}
    </select>
  );
};

export default SimpleSelect;
