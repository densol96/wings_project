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
  temp: number;
};

const Select = ({ className, optionClassName, selectDict, activeValue }: Props) => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const optionValue = e.target.value; // format: sorting-direction f.e. createdAt-desc
    const [sorting, direction] = optionValue.split("-");
    const params = new URLSearchParams(searchParams);
    params.set("sort", sorting);
    params.set("direction", direction);
    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  return (
    <div className="flex items-center gap-2">
      <label className="normal-case" htmlFor="sortProductBy">
        {selectDict.label}
      </label>
      <select value={activeValue} onChange={onChange} id="sortProductBy" className={cn("border-1 border-gray-300 py-1", className)}>
        {selectDict.options.map((opt) => (
          <option key={opt.label + "_" + opt.value} className={cn("text-center", optionClassName)} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default Select;
