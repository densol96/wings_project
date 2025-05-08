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
};

const FilterSelect = ({ className, optionClassName, selectDict, activeValue, id, filterLabel }: Props) => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const params = new URLSearchParams(searchParams);
    params.set(filterLabel, e.target.value);
    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  return (
    <div className="flex items-center gap-2">
      <label className="normal-case" htmlFor={id}>
        {selectDict.label}
      </label>
      <select value={activeValue} onChange={onChange} id={id} className={cn("border-1 border-gray-300 py-1", className)}>
        {selectDict.options.map((opt) => (
          <option key={opt.label + "_" + opt.value} className={cn("text-center", optionClassName)} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default FilterSelect;
