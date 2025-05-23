import ProductForm from "../ProductForm";
import { loadProductsMeta } from "../utils";

const Page = async () => {
  return <ProductForm requiredMeta={await loadProductsMeta()} />;
};

export default Page;
