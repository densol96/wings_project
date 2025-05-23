"use client";

import FilterSelect from "@/components/ui/FilterSelect";
import { useLoadData } from "@/hooks/useLoadData";
import { CategoryLi } from "@/types";

type Props = {
  categoryId?: number;
  onChangeAlt?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  name?: string;
  allCategories: CategoryLi[];
  disabled?: boolean;
};

const CategoryFilter = ({ categoryId, onChangeAlt, name, allCategories, disabled = false }: Props) => {
  // When we are in the form => we provide a function to update a form state with the select value on change
  const isInForm = !!onChangeAlt;

  // categoryId of 0 is a specially reserved category in this case - "All products" (NOT A REAL CATEGORY => when length <= 1 => no categories)
  // if error => for now, not rendering (meaning unable to submit or filter results) is ok. later can add more complex error handling here []
  if (allCategories?.length && allCategories.length <= 1) return null;

  return (
    <FilterSelect
      id={"filterByProductCategory"}
      activeValue={categoryId ? categoryId + "" : isInForm ? "" : "0"}
      selectDict={{
        label: "Kategorija",
        options:
          allCategories?.map((c) =>
            isInForm && c.id === 0 ? { label: "Lūdzu, izvēlieties kategoriju.", value: "", disabled: true } : { label: c.title, value: c.id + "" }
          ) || [],
      }}
      filterLabel="categoryId"
      onChangeAlt={onChangeAlt}
      name={name}
      disabled={disabled}
    />
  );
};

export default CategoryFilter;
