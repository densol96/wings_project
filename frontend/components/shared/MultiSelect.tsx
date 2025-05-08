"use client";

import { cn } from "@/utils";
import Select from "react-select";

type SelectOption<T> = {
  value: T;
  label: string;
};

type Props<T> = {
  selectedValues: T[];
  name: string;
  placeholder?: string;
  options: SelectOption<T>[];
  label: string;
  className?: string;
  setSelectedRolesIds: (newValues: T[]) => void;
};

const MultiSelect = <T,>({ selectedValues, options, name, placeholder, label, className, setSelectedRolesIds }: Props<T>) => {
  const selectedOptions = options.filter((opt) => selectedValues.includes(opt.value));

  return (
    <div className={cn("mb-4", className)}>
      <label className="block mb-1 font-medium">{label}</label>
      <Select
        isMulti
        name={name}
        options={options}
        defaultValue={selectedOptions}
        className="basic-multi-select"
        classNamePrefix="select"
        placeholder={placeholder}
        onChange={(selected) => setSelectedRolesIds(selected.map((s) => s.value))}
      />
    </div>
  );
};

export default MultiSelect;
