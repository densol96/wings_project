"use server";

import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { revalidatePath } from "next/cache";

export const deleteProduct = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  return await serverFetchAction({
    endpoint: `admin/products/${formData.get("id")}`,
    method: "DELETE",
    alternativeOk: () => {
      revalidatePath("/", "layout");
    },
  });
};

export default deleteProduct;
