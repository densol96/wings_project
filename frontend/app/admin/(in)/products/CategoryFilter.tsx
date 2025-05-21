import FilterSelect from "@/components/ui/FilterSelect";
import { CategoryLi } from "@/types";
import { fetcher } from "@/utils";

type Props = {
  categoryId?: number;
};

const CategoryFilter = async ({ categoryId }: Props) => {
  const categories = await fetcher<CategoryLi[]>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/product-categories`);
  return (
    <FilterSelect
      id="filterByProductCategory"
      activeValue={categoryId ? categoryId + "" : "0"}
      selectDict={{
        label: "Kategorija",
        options: categories.map((c) => ({ label: c.title, value: c.id + "" })),
      }}
      filterLabel="categoryId"
    />
  );
};

export default CategoryFilter;
