import { ExistingCategoryDto, IdParams } from "@/types";
import ProductForm from "../../CategoriesForm";
import { adminFetch } from "@/actions/helpers/adminFetch";
import { existingCategoryToForm } from "../../../utils";

const Page = async ({ params: { id } }: IdParams) => {
  const category = await adminFetch<ExistingCategoryDto>(`products/categories/${id}`);
  return <ProductForm existingCategory={existingCategoryToForm(category)} />;
};

export default Page;
