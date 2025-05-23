"use client";

import Select from "react-select";

import { ColorDto } from "@/types";
import { useLoadData } from "@/hooks/useLoadData";
import { Label, Spinner } from "@/components";

type Props = {
  selectedColorIds: number[];
  className?: string;
  setSelectedColorIds: (selectedIds: number[]) => void;
  allColors: ColorDto[];
  disabled?: boolean;
};

const ProductColors = ({ selectedColorIds, className, setSelectedColorIds, allColors, disabled }: Props) => {
  const options = allColors?.map((c) => ({ value: c.id, label: c.name })) || [];

  return (
    <div className={className}>
      <Label name="colors" label="Krāsas" />
      <Select
        isMulti
        name="colors"
        options={options}
        value={options?.filter((c) => selectedColorIds.includes(c.value))}
        className="basic-multi-select"
        classNamePrefix="select"
        placeholder="Izvēlieties krāsas, kas veido jūsu produkta paleti."
        onChange={(selected) => setSelectedColorIds(selected.map((s) => s.value))}
        isDisabled={disabled}
      />
    </div>
  );
};

export default ProductColors;
