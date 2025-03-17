"use client";

import React from "react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";

import { SelectOption } from "@/types";
import { cn } from "@/utils";

type Props = {
  className?: string;
  optionClassName?: string;
  options: SelectOption[];
  page: number;
};

const Select = ({ className, optionClassName, options, page }: Props) => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const optionValue = e.target.value; // format: sorting-direction f.e. createdAt-desc
    const [sorting, direction] = optionValue.split("-");
    const params = new URLSearchParams(searchParams);
    params.set("sorting", sorting);
    params.set("direction", direction);
    // params.set("page", `${page}`);
    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  return (
    <select onChange={onChange} id="sortProductBy" className={cn("border-1 border-gray-300 py-1", className)}>
      {options.map((opt) => (
        <option key={opt.label + "_" + opt.value} className={cn("text-center", optionClassName)} value={opt.value}>
          {opt.label}
        </option>
      ))}
    </select>
  );
};

export default Select;
