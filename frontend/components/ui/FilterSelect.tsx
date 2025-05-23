"use client";

import React from "react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { cn } from "@/utils";
import { SelectOptions } from "@/types/sections/shop";

type Props = {
  className?: string;
  optionClassName?: string;
  selectDict: SelectOptions;
  activeValue: string;
  id: string;
  filterLabel: string;
  onChangeAlt?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  name?: string;
  disabled?: boolean;
};

/*
regarding onChangeAlt 
** 
Want to reuse this component for my admin create-update (product) section.
There, the new value is just required to update state.
Otherwise, to update URL to trigger new GET request to the parent Server Component.
*/

const FilterSelect = ({ className, optionClassName, selectDict, activeValue, id, filterLabel, onChangeAlt, name, disabled = false }: Props) => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { value } = e.target;
    const params = new URLSearchParams(searchParams);
    params.set("page", "1");
    if (value) params.set(filterLabel, e.target.value); // f.e, label: "All" => value: "" => no filter will be applied on the backend
    else params.delete(filterLabel);
    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  return (
    <div className="flex items-center gap-2">
      {!onChangeAlt && (
        <label className="normal-case" htmlFor={id}>
          {selectDict.label}
        </label>
      )}
      <select
        disabled={disabled}
        name={name}
        value={activeValue}
        onChange={onChangeAlt || onChange}
        id={id}
        className={cn("border-1 border-gray-300 py-1", className)}
      >
        {selectDict.options.map((opt) => (
          <option disabled={opt.disabled === true} key={opt.label + "_" + opt.value} className={cn("text-center", optionClassName)} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default FilterSelect;
