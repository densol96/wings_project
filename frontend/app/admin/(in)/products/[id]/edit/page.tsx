import { adminFetch } from "@/actions/helpers/adminFetch";
import ProductForm from "../../ProductForm";
import { existingProductToForm, loadProductsMeta } from "../../utils";
import { ExistingProductDto, IdParams, ProductUpdateDto } from "@/types";

const Page = async ({ params: { id } }: IdParams) => {
  const [existingProduct, requiredMeta] = await Promise.all([adminFetch<ExistingProductDto>(`products/${id}`), loadProductsMeta()]);
  return <ProductForm existingProduct={existingProductToForm(existingProduct)} requiredMeta={requiredMeta} />;
};

export default Page;
