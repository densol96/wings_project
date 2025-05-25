"use server";

import { serverFetchAction } from "@/actions/helpers/serverFetchAction";
import { FormState } from "@/types";
import { revalidatePath } from "next/cache";

export const addImages = async (formData: FormData, productId: number): Promise<FormState> => {
  const res = await serverFetchAction<FormState>({
    endpoint: `admin/products/${productId}/images`,
    method: "POST",
    body: formData,
    alternativeOk: () => revalidatePath("/", "layout"),
    isMultipart: true,
  });
  console.log(res);
  return res;
};

export default addImages;
