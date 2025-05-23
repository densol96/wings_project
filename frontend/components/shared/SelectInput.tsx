"use client";

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
  setSelected: (newValues: T[]) => void;
};

const MultiSelectInput = <T,>({ selectedValues, name, placeholder, options, label, className, setSelected }: Props<T>) => {
  const selectedOptions = options.filter((opt) => selectedValues.includes(opt.value));
  return (
    <Select
      isMulti
      name={name}
      options={options}
      defaultValue={selectedOptions}
      className="basic-multi-select"
      classNamePrefix="select"
      placeholder={placeholder}
      onChange={(selected) => setSelected(selected?.map((s) => s.value))}
    />
  );
};

export default MultiSelectInput;
