"use client";

import { Input } from "@/components";
import { Permission } from "@/types";
import { usePathname, useRouter, useSearchParams } from "next/navigation";

type Props = {
  name: string;
  options: {
    value: string | number;
    label: string;
  }[];
  className?: string;
};

const CheckboxGroup = ({ name, options }: Props) => {
  const searchParams = useSearchParams();
  const router = useRouter();
  const pathname = usePathname();

  const selectedPermissions = searchParams.getAll(name);

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    const checked = e.target.checked;

    const params = new URLSearchParams(searchParams);
    const current = new Set(params.getAll(name));

    if (checked) {
      current.add(value);
    } else {
      current.delete(value);
    }

    params.delete(name);
    console.log(current);
    current.forEach((val) => params.append(name, val));

    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  return options.map((option) => (
    <label key={option.label + "-" + option.value} className="flex items-center gap-2 text-sm">
      <Input
        type="checkbox"
        name={name}
        value={option.value + ""}
        checked={selectedPermissions.includes(option.value + "")}
        onChange={(e) => handleCheckboxChange(e as React.ChangeEvent<HTMLInputElement>)}
      />
      {option.label}
    </label>
  ));
};

export default CheckboxGroup;
