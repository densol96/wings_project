"use server";

import { serverFetchAction } from "@/actions/helpers/serverFetchAction";
import { FormState } from "@/types";
import { revalidatePath } from "next/cache";

export const deleteProductCategory = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  return await serverFetchAction({
    endpoint: `admin/products/categories/${formData.get("id")}`,
    method: "DELETE",
    alternativeOk: () => {
      revalidatePath("/", "layout");
    },
  });
};

export default deleteProductCategory;
